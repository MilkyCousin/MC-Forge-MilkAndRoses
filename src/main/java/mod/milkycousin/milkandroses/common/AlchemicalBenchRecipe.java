package mod.milkycousin.milkandroses.common;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.*;
import mod.milkycousin.milkandroses.MilkAndRoses;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("deprecation")
public class AlchemicalBenchRecipe implements IRecipe<IInventory>
{
    private static final int MAX_HEIGHT = 3;
    private static final int MAX_WIDTH = 3;

    private final int manaVesselsCount;
    private final NonNullList<Ingredient> matrixIngredients;
    private final ItemStack outStack;
    private final ResourceLocation id;

    public AlchemicalBenchRecipe(ResourceLocation idIn, NonNullList<Ingredient> ingredientsIn, ItemStack resultIn, int manaIn)
    {
        this.id = idIn;
        this.matrixIngredients = ingredientsIn;
        this.outStack = resultIn;
        this.manaVesselsCount = manaIn;
    }

    @Override
    public boolean matches(IInventory inv, World worldIn)
    {
        return false;
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv)
    {
        return this.getRecipeOutput().copy();
    }

    @Override
    public NonNullList<Ingredient> getIngredients()
    {
        return this.matrixIngredients;
    }

    @Override
    public boolean canFit(int width, int height)
    {
        return false;
    }

    @Override
    public ItemStack getRecipeOutput()
    {
        return this.outStack;
    }

    @Override
    public ResourceLocation getId()
    {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer()
    {
        return null;
    }

    @Override
    public IRecipeType<?> getType()
    {
        return null;
    }

    public int getManaCount()
    {
        return this.manaVesselsCount;
    }


    public int getWidth() {
        return MAX_WIDTH;
    }

    public int getHeight() {
        return MAX_HEIGHT;
    }

    // TODO: rewrite methods below. shame on me because of laziness

    private static Map<String, Ingredient> retreiveMapping(JsonObject jsonObject)
    {
        Map<String, Ingredient> map = Maps.newHashMap();

        for(Map.Entry<String, JsonElement> entry : jsonObject.entrySet())
        {
            if (entry.getKey().length() != 1)
            {
                throw new JsonSyntaxException("Invalid key entry given: (length > 1 or == 0)" + (String)entry.getKey());
            }

            if (" ".equals(entry.getKey()))
            {
                throw new JsonSyntaxException("Invalid key entry given: (' ' is a reserved symbol)" + (String)entry.getKey());
            }

            map.put(entry.getKey(), Ingredient.deserialize(entry.getValue()));
        }

        map.put(" ", Ingredient.EMPTY);
        return map;
    }

    private static NonNullList<Ingredient> retreiveIngredientsByMapping(String[] pattern, Map<String, Ingredient> keys, int patternWidth, int patternHeight) {
        NonNullList<Ingredient> nonnulllist = NonNullList.withSize(patternWidth * patternHeight, Ingredient.EMPTY);
        Set<String> set = Sets.newHashSet(keys.keySet());
        set.remove(" ");

        for(int i = 0; i < pattern.length; ++i) {
            for(int j = 0; j < pattern[i].length(); ++j) {
                String s = pattern[i].substring(j, j + 1);
                Ingredient ingredient = keys.get(s);
                if (ingredient == null) {
                    throw new JsonSyntaxException("Pattern references symbol '" + s + "' but it's not defined in the key");
                }

                set.remove(s);
                nonnulllist.set(j + patternWidth * i, ingredient);
            }
        }

        if (!set.isEmpty()) {
            throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set);
        } else {
            return nonnulllist;
        }
    }

    static String[] shrink(String... toShrink) {
        int i = Integer.MAX_VALUE;
        int j = 0;
        int k = 0;
        int l = 0;

        for(int i1 = 0; i1 < toShrink.length; ++i1) {
            String s = toShrink[i1];
            i = Math.min(i, firstNonSpace(s));
            int j1 = lastNonSpace(s);
            j = Math.max(j, j1);
            if (j1 < 0) {
                if (k == i1) {
                    ++k;
                }

                ++l;
            } else {
                l = 0;
            }
        }

        if (toShrink.length == l) {
            return new String[0];
        } else {
            String[] astring = new String[toShrink.length - l - k];

            for(int k1 = 0; k1 < astring.length; ++k1) {
                astring[k1] = toShrink[k1 + k].substring(i, j + 1);
            }

            return astring;
        }
    }

    private static int firstNonSpace(String str) {
        int i;
        for(i = 0; i < str.length() && str.charAt(i) == ' '; ++i) {
            ;
        }

        return i;
    }

    private static int lastNonSpace(String str) {
        int i;
        for(i = str.length() - 1; i >= 0 && str.charAt(i) == ' '; --i) {
            ;
        }

        return i;
    }

    private static String[] retreivePatternRecipe(JsonArray jsonArr) {
        String[] astring = new String[jsonArr.size()];
        if (astring.length > MAX_HEIGHT) {
            throw new JsonSyntaxException("Invalid pattern: too many rows, " + MAX_HEIGHT + " is maximum");
        } else if (astring.length == 0) {
            throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
        } else {
            for(int i = 0; i < astring.length; ++i) {
                String s = JSONUtils.getString(jsonArr.get(i), "pattern[" + i + "]");
                if (s.length() > MAX_WIDTH) {
                    throw new JsonSyntaxException("Invalid pattern: too many columns, " + MAX_WIDTH + " is maximum");
                }

                if (i > 0 && astring[0].length() != s.length()) {
                    throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
                }

                astring[i] = s;
            }

            return astring;
        }
    }

    private static ItemStack retreiveItem(JsonObject jsonObject)
    {
        String s = JSONUtils.getString(jsonObject, "item");
        Item item = Registry.ITEM.getValue(new ResourceLocation(s)).orElseThrow(() ->
                new JsonSyntaxException("Unknown item '" + s + "'")
        );
        if (jsonObject.has("data"))
        {
            throw new JsonParseException("Disallowed data tag found");
        }
        else {
            int i = JSONUtils.getInt(jsonObject, "count", 1);
            return net.minecraftforge.common.crafting.CraftingHelper.getItemStack(jsonObject, true);
        }
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<AlchemicalBenchRecipe>
    {
        private ResourceLocation RECIPES_LOCATION = new ResourceLocation(MilkAndRoses.MODID, "alchemical_recipes");

        @Override
        public AlchemicalBenchRecipe read(ResourceLocation recipeId, JsonObject json)
        {
            Map<String, Ingredient> keyMapping = AlchemicalBenchRecipe.retreiveMapping(
                    JSONUtils.getJsonObject(json, "key")
            );
            String[] rawRecipe = AlchemicalBenchRecipe.shrink(AlchemicalBenchRecipe.retreivePatternRecipe(
                    JSONUtils.getJsonArray(json, "pattern")
            ));
            NonNullList<Ingredient> recipeIn = AlchemicalBenchRecipe.retreiveIngredientsByMapping(
                    rawRecipe, keyMapping, MAX_WIDTH, MAX_HEIGHT
            );
            ItemStack outStackIn = AlchemicalBenchRecipe.retreiveItem(
                    JSONUtils.getJsonObject(json, "results")
            );
            //int i = rawRecipe[0].length();
            //int j = rawRecipe.length();
            int manaCost = JSONUtils.getInt(json, "cost");

            return new AlchemicalBenchRecipe(recipeId, recipeIn, outStackIn, manaCost);
        }

        @Nullable
        @Override
        public AlchemicalBenchRecipe read(ResourceLocation recipeId, PacketBuffer buffer)
        {
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(MAX_HEIGHT * MAX_WIDTH, Ingredient.EMPTY);

            for(int k = 0; k < nonnulllist.size(); ++k) {
                nonnulllist.set(k, Ingredient.read(buffer));
            }

            ItemStack itemstack = buffer.readItemStack();

            int manaVessels = buffer.readVarInt();

            return new AlchemicalBenchRecipe(recipeId, nonnulllist, itemstack, manaVessels);
        }

        @Override
        public void write(PacketBuffer buffer, AlchemicalBenchRecipe recipe)
        {
            for(Ingredient ingredient : recipe.matrixIngredients)
            {
                ingredient.write(buffer);
            }

            buffer.writeItemStack(recipe.outStack);
            buffer.writeVarInt(recipe.manaVesselsCount);
        }
    }
}