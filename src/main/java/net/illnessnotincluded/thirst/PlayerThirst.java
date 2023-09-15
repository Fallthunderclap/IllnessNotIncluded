package net.illnessnotincluded.thirst;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class PlayerThirst {
    private float thirst;
    private final float MIN_THIRST = 0;
    private final float MAX_THIRST = 10;

    public float getThirst() {
        return thirst;
    }

    public void addThirst(float add) {
        this.thirst = Math.min(thirst + add, MAX_THIRST);
    }

    public void subThirst(float sub) {
        this.thirst = Math.max(thirst - sub, MIN_THIRST);
    }

    public void copyThirst(PlayerThirst source) {
        this.thirst = source.thirst;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putFloat("thirst", thirst);
    }

    public void loadNBTData(CompoundTag nbt) {
        thirst = nbt.getFloat("thirst");
    }
}

