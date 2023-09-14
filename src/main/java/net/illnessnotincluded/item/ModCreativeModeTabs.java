package net.illnessnotincluded.item;

import net.illnessnotincluded.IllnessNotIncluded;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, IllnessNotIncluded.MOD_ID);

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    public static final RegistryObject<CreativeModeTab> ILLNESS_NOT_INCLUDED =
            CREATIVE_MODE_TABS.register("Illness Not Included",
                    () -> CreativeModeTab.builder()
                            .icon(() -> new ItemStack(ModItems.PILL.get()))
                            .title(Component.translatable("creative_tab.illness_not_included_tab"))
                            .displayItems((pParameters, pOutput) -> {
                                pOutput.accept(ModItems.MASK.get());
                                pOutput.accept(ModItems.PILL.get());
                            })
                            .build());
}
