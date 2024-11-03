package com.feliscape.wintershine.content.attachment;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;

public class MobEffectData implements INBTSerializable<CompoundTag> {
    public class Siphon{
        private int siphonedHearts;
        public static int MAX_SIPHON = 30;

        public int getSiphonedHearts() {
            return siphonedHearts;
        }

        public void setSiphonedHearts(int siphonedHearts) {
            this.siphonedHearts = siphonedHearts;
        }

        public CompoundTag save(){
            CompoundTag nbt = new CompoundTag();
            nbt.putInt("SiphonedHearts", this.siphonedHearts);
            return nbt;
        }
        public void saveTo(CompoundTag nbt){
            nbt.putInt("SiphonedHearts", this.siphonedHearts);
        }
        public void load(CompoundTag nbt){
            this.siphonedHearts = nbt.getInt("SiphonedHearts");
        }

        public void siphonHearts(float damage) {
            this.siphonedHearts += Math.min(Math.round(damage / 2F), MAX_SIPHON);
        }

        public void reset() {
            this.siphonedHearts = 0;
        }
    }

    public final Siphon siphon = new Siphon();

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();

        tag.put("Siphon", this.siphon.save());

        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag compoundTag) {
        this.siphon.load(compoundTag.getCompound("Siphon"));
    }
}
