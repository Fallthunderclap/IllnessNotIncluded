package net.illnessnotincluded.item;

import net.illnessnotincluded.IllnessNotIncluded;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems extends Item {
    public static final DeferredRegister<Item> ITEM_DEFERRED_REGISTER =
            DeferredRegister.create(ForgeRegistries.ITEMS, IllnessNotIncluded.MOD_ID);

    public static void register(IEventBus eventBus) {
        ITEM_DEFERRED_REGISTER.register(eventBus);
    }

    public static final RegistryObject<Item> SURGICAL_MASK =
            ITEM_DEFERRED_REGISTER.register("surgical_mask", () -> new Item(new Item.Properties()));

    public ModItems(Properties properties) {
        super(properties);
    }
}
