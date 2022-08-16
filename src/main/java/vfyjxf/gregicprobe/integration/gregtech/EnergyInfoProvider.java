package vfyjxf.gregicprobe.integration.gregtech;

import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IEnergyContainer;
import gregtech.integration.theoneprobe.provider.CapabilityInfoProvider;
import mcjty.theoneprobe.api.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import vfyjxf.gregicprobe.config.GregicProbeConfig;

public class EnergyInfoProvider extends CapabilityInfoProvider<IEnergyContainer> {

    private static final char[] ENCODED_POSTFIXES = "KMGTPE".toCharArray();

    @Override
    protected Capability<IEnergyContainer> getCapability() {
        return GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER;
    }

    @Override
    protected boolean allowDisplaying(IEnergyContainer capability) {
        return !capability.isOneProbeHidden();
    }

    @Override
    protected void addProbeInfo(IEnergyContainer capability, IProbeInfo probeInfo, EntityPlayer entityPlayer, TileEntity tileEntity, IProbeHitData iProbeHitData) {
        long energyStored = capability.getEnergyStored();
        long maxStorage = capability.getEnergyCapacity();
        if (maxStorage == 0) return;
        IProbeInfo horizontalPane = probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
        String additionalSpacing = tileEntity.hasCapability(GregtechTileCapabilities.CAPABILITY_WORKABLE, null) ? "   " : "";
        horizontalPane.text(TextStyleClass.INFO + "{*gregtech.top.energy_stored*} " + additionalSpacing);
        if(GregicProbeConfig.displayEnergyThousandths) {
            horizontalPane.progress(energyStored, maxStorage, probeInfo.defaultProgressStyle()
                    .numberFormat(NumberFormat.NONE)
                    .suffix(getSuffix(energyStored) + " / " + getSuffix(maxStorage))
                    .borderColor(GregicProbeConfig.borderColorEnergy)
                    .backgroundColor(0x00000000)
                    .filledColor(GregicProbeConfig.filledColorEnergy)
                    .alternateFilledColor(GregicProbeConfig.alternateFilledColorEnergy));
        }else {
            horizontalPane.progress(energyStored, maxStorage, probeInfo.defaultProgressStyle()
                    .suffix("/" + maxStorage + " EU")
                    .borderColor(GregicProbeConfig.borderColorEnergy)
                    .backgroundColor(GregicProbeConfig.backgroundColorEnergy)
                    .filledColor(GregicProbeConfig.filledColorEnergy)
                    .alternateFilledColor(GregicProbeConfig.alternateFilledColorEnergy));
        }
    }

    @Override
    public String getID() {
        return "gregtech:energy_container_provider";
    }

    private String getSuffix(long energyNumber) {
        String energy = Long.toString(energyNumber);
        if (energy.length() <= 3) {
            return energy;
        }

        long base = energyNumber;
        int exponent = -1;
        String reslut = "";
        while (Long.toString(base).length() > 3) {
            reslut = String.format("%.2f", (double) base / 1000);
            base = base / 1000;
            exponent++;
        }

        return reslut + ENCODED_POSTFIXES[exponent];
    }

}
