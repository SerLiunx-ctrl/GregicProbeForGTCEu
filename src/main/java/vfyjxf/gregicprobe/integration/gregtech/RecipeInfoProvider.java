package vfyjxf.gregicprobe.integration.gregtech;

import gregtech.api.GTValues;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IWorkable;
import gregtech.api.capability.impl.AbstractRecipeLogic;
import gregtech.api.util.GTUtility;
import gregtech.integration.theoneprobe.provider.CapabilityInfoProvider;
import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.TextStyleClass;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.capabilities.Capability;

public class RecipeInfoProvider extends CapabilityInfoProvider<IWorkable> {

    @Override
    protected Capability<IWorkable> getCapability() {
        return GregtechTileCapabilities.CAPABILITY_WORKABLE;
    }

    @Override
    protected void addProbeInfo(IWorkable capability, IProbeInfo probeInfo, EntityPlayer entityPlayer, TileEntity tileEntity, IProbeHitData iProbeHitData) {
        if (capability instanceof AbstractRecipeLogic) {
            if (((AbstractRecipeLogic) capability).getRecipeEUt() > 0) {
                IProbeInfo horizontalPane = probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
                horizontalPane.text(TextStyleClass.INFO + "{*gregicprobe:top.eut*} ");
                long currentEut = ((AbstractRecipeLogic) capability).getRecipeEUt();
                horizontalPane.text(TextStyleClass.INFO.toString() + currentEut + " EU/t" + TextFormatting.YELLOW + String.format(" (%s)", GTValues.VN[GTUtility.getTierByVoltage(currentEut)]));
            }
        }
    }

    @Override
    public String getID() {
        return "gregicprobe:recipe_info_eut_provider";
    }
}
