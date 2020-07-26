package mod.milkycousin.milkandroses.common;

import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.screen.*;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.Map;

@SuppressWarnings("deprecation")
@OnlyIn(Dist.CLIENT)
public class MilkyScreenManager
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Map<ContainerType<?>, ScreenManager.IScreenFactory<?, ?>> FACTORY_MAP = Maps.newHashMap();

    public static <T extends Container> void openScreen(@Nullable ContainerType<T> type, Minecraft mc, int windowId, ITextComponent title) {
        getScreenFactory(type, mc, windowId, title).ifPresent(f -> f.createScreen(title, type, mc, windowId));
    }

    public static <T extends Container> java.util.Optional<ScreenManager.IScreenFactory<T, ?>> getScreenFactory(@Nullable ContainerType<T> type, Minecraft mc, int windowId, ITextComponent title) {
        if (type == null) {
            LOGGER.warn("Trying to open invalid screen with name: {}", (Object)title.getString());
        } else {
            ScreenManager.IScreenFactory<T, ?> iscreenfactory = getFactory(type);
            if (iscreenfactory == null) {
                LOGGER.warn("Failed to create screen for menu type: {}", (Object) Registry.MENU.getKey(type));
            } else {
                return java.util.Optional.of(iscreenfactory);
            }
        }
        return java.util.Optional.empty();
    }

    @Nullable
    private static <T extends Container> ScreenManager.IScreenFactory<T, ?> getFactory(ContainerType<T> type) {
        return (ScreenManager.IScreenFactory<T, ?>)FACTORY_MAP.get(type);
    }

    public static <M extends Container, U extends Screen & IHasContainer<M>> void registerFactory(ContainerType<? extends M> type, ScreenManager.IScreenFactory<M, U> factory) {
        ScreenManager.IScreenFactory<?, ?> iscreenfactory = FACTORY_MAP.put(type, factory);
        if (iscreenfactory != null) {
            throw new IllegalStateException("Duplicate registration for " + Registry.MENU.getKey(type));
        }
    }

    public static boolean isMissingScreen() {
        boolean flag = false;

        for(ContainerType<?> containertype : Registry.MENU) {
            if (!FACTORY_MAP.containsKey(containertype)) {
                LOGGER.debug("Menu {} has no matching screen", (Object)Registry.MENU.getKey(containertype));
                flag = true;
            }
        }

        return flag;
    }

    @OnlyIn(Dist.CLIENT)
    public interface IScreenFactory<T extends Container, U extends Screen & IHasContainer<T>> {
        default void createScreen(ITextComponent title, ContainerType<T> type, Minecraft mc, int windowId) {
            U u = this.create(type.create(windowId, mc.player.inventory), mc.player.inventory, title);
            mc.player.openContainer = ((IHasContainer)u).getContainer();
            mc.displayGuiScreen(u);
        }

        U create(T p_create_1_, PlayerInventory p_create_2_, ITextComponent p_create_3_);
    }
}
