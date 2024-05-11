package dev.isxander.optipainting.mixins;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;

/*? if >1.20.6 {*//*
@Mixin(PaintingVariant.class)
public class PaintingVariantMixin {
    @ModifyExpressionValue(method = "method_59948", at = @At(value = "CONSTANT", args = "intValue=16"))
    private static int modifyMaxPaintingSize(int oldMaxSize) {
        return Integer.MAX_VALUE;
    }
}
*//*? } else {*/
@Mixin(Minecraft.class)
public class PaintingVariantMixin {
}
/*?}*/
