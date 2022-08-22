package vfyjxf.gregicprobe.integration.gregtech;

import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IWorkable;
import gregtech.integration.theoneprobe.provider.CapabilityInfoProvider;
import mcjty.theoneprobe.api.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.capabilities.Capability;
import vfyjxf.gregicprobe.config.GregicProbeConfig;

public class WorkableInforProvider extends CapabilityInfoProvider<IWorkable> {

    public WorkableInforProvider() {

    }

    @Override
    protected Capability<IWorkable> getCapability() {
        return GregtechTileCapabilities.CAPABILITY_WORKABLE;
    }

    @Override
    protected void addProbeInfo(IWorkable capability, IProbeInfo probeInfo, EntityPlayer entityPlayer, TileEntity tileEntity, IProbeHitData iProbeHitData) {
        int currentProgress = capability.getProgress();
        int maxProgress = capability.getMaxProgress();
        if (currentProgress > 0) {
            IProbeInfo horizontalPane = probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
            horizontalPane.text(TextStyleClass.INFO + "{*gregtech.top.progress*} ");
            horizontalPane.progress(currentProgress, maxProgress, probeInfo.defaultProgressStyle()
                    .numberFormat(NumberFormat.NONE)
                    .suffix(String.format("%.1fs / %.1fs", (double) currentProgress / 20.0D, (double) maxProgress / 20.0D))
                    .borderColor(GregicProbeConfig.borderColorProgress)
                    .backgroundColor(GregicProbeConfig.backgroundColorProgress)
                    .filledColor(GregicProbeConfig.filledColorProgress)
                    .alternateFilledColor(GregicProbeConfig.alternateFilledColorProgress));

        }
    }

    @Override
    public String getID() {
        return "gregtech:workable_provider";
    }
}

