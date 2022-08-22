package vfyjxf.gregicprobe.integration.gregtech;

import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IWorkable;
import gregtech.api.capability.impl.AbstractRecipeLogic;
import gregtech.integration.theoneprobe.provider.CapabilityInfoProvider;
import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.TextStyleClass;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import vfyjxf.gregicprobe.config.GregicProbeConfig;

import java.util.ArrayList;
import java.util.List;

public class RecipeItemOutputInfoProvider extends CapabilityInfoProvider<IWorkable> {

    @Override
    protected Capability<IWorkable> getCapability() {
        return GregtechTileCapabilities.CAPABILITY_WORKABLE;
    }

    @Override
    protected void addProbeInfo(IWorkable capability, IProbeInfo probeInfo, EntityPlayer entityPlayer, TileEntity tileEntity, IProbeHitData iProbeHitData) {
        if (capability.getProgress() > 0 && capability instanceof AbstractRecipeLogic) {
            IProbeInfo horizontalPane = probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
            List<ItemStack> itemOutputs = new ArrayList<>(
                    ObfuscationReflectionHelper.getPrivateValue(
                            AbstractRecipeLogic.class,
                            (AbstractRecipeLogic) capability,
                            "itemOutputs")
            );

            if (!itemOutputs.isEmpty()) {
                horizontalPane.text(TextStyleClass.INFO + "{*gregicprobe:top.item.outputs*} ");
                for (ItemStack itemOutput : itemOutputs) {
                    if (itemOutput != null) {
                        horizontalPane.item(itemOutput);
                        if (GregicProbeConfig.displayItemName && itemOutputs.size() <= 2) {
                            horizontalPane.itemLabel(itemOutput);
                        }
                    }
                }
            }
        }
    }

    @Override
    public String getID() {
        return "gregicprobe:recipe_info_item_output";
    }

}