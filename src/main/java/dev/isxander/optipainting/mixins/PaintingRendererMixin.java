package dev.isxander.optipainting.mixins;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.entity.PaintingRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/*? if >1.20.2 {*/
import com.mojang.blaze3d.vertex.PoseStack;
/*? } elif >1.19.2 {*//*
import org.joml.Matrix3f;
import org.joml.Matrix4f;
*//*?} else {*//*
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
*//*?}*/

@Mixin(PaintingRenderer.class)
public class PaintingRendererMixin {
    @Unique
    private static final String vertexTarget =
            /*? if >1.20.2 {*/
            "Lnet/minecraft/client/renderer/entity/PaintingRenderer;vertex(Lcom/mojang/blaze3d/vertex/PoseStack$Pose;Lcom/mojang/blaze3d/vertex/VertexConsumer;FFFFFIIII)V";
            /*?} elif >1.19.2 {*//*
            "Lnet/minecraft/client/renderer/entity/PaintingRenderer;vertex(Lorg/joml/Matrix4f;Lorg/joml/Matrix3f;Lcom/mojang/blaze3d/vertex/VertexConsumer;FFFFFIIII)V";
            *//*?} else {*//*
            "Lnet/minecraft/client/renderer/entity/PaintingRenderer;vertex(Lcom/mojang/math/Matrix4f;Lcom/mojang/math/Matrix3f;Lcom/mojang/blaze3d/vertex/VertexConsumer;FFFFFIIII)V";
            *//*?}*/


    @Inject(method = "renderPainting", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;getLightColor(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/core/BlockPos;)I"))
    private void addBoundaryChecks(
            CallbackInfo ci,
            /*? if >1.20.6 {*//*
            @Local(argsOnly = true, ordinal = 0) int pWidth,
            @Local(argsOnly = true, ordinal = 1) int pHeight,
            @Local(ordinal = 2) int blockX,
            @Local(ordinal = 3) int blockY,
            *//*?} else {*/
            @Local(ordinal = 2) int pWidth,
            @Local(ordinal = 3) int pHeight,
            @Local(ordinal = 4) int blockX,
            @Local(ordinal = 5) int blockY,
            /*?}*/
            @Share("northBoundary")LocalBooleanRef northBoundary,
            @Share("eastBoundary")LocalBooleanRef eastBoundary,
            @Share("southBoundary")LocalBooleanRef southBoundary,
            @Share("westBoundary")LocalBooleanRef westBoundary
    ) {
        northBoundary.set(blockY == pHeight - 1);
        westBoundary.set(blockX == pWidth - 1);
        southBoundary.set(blockY == 0);
        eastBoundary.set(blockX == 0);
    }

    @WrapWithCondition(
            method = "renderPainting",
            at = {
                    @At(ordinal = 4, value = "INVOKE", target = vertexTarget),
                    @At(ordinal = 5, value = "INVOKE", target = vertexTarget),
                    @At(ordinal = 6, value = "INVOKE", target = vertexTarget),
                    @At(ordinal = 7, value = "INVOKE", target = vertexTarget)
            }
    )
    private boolean preventBackFace(
            PaintingRenderer instance,
            /*? if >1.20.2 {*/
            PoseStack.Pose pose, VertexConsumer vertexConsumer, float f, float g, float h, float i, float j, int k, int l, int m, int n,
            /*? } elif >1.19.2 {*//*
            Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer vertexConsumer, float f, float g, float u, float v, float h, int i, int j, int k, int lightmapUV,
            *//*? } else {*//*
            Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer vertexConsumer, float f, float g, float u, float v, float h, int i, int j, int k, int l,
            *//*?}*/
            @Share("northBoundary")LocalBooleanRef northBoundary
    ) {
        return false; // don't think i can optimise this unless querying blocks
    }

    @WrapWithCondition(
            method = "renderPainting",
            at = {
                    @At(ordinal = 8, value = "INVOKE", target = vertexTarget),
                    @At(ordinal = 9, value = "INVOKE", target = vertexTarget),
                    @At(ordinal = 10, value = "INVOKE", target = vertexTarget),
                    @At(ordinal = 11, value = "INVOKE", target = vertexTarget)
            }
    )
    private boolean preventNorthFrame(
            PaintingRenderer instance,
            /*? if >1.20.2 {*/
            PoseStack.Pose pose, VertexConsumer vertexConsumer, float f, float g, float h, float i, float j, int k, int l, int m, int n,
            /*? } elif >1.19.2 {*//*
            Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer vertexConsumer, float f, float g, float u, float v, float h, int i, int j, int k, int lightmapUV,
            *//*? } else {*//*
            Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer vertexConsumer, float f, float g, float u, float v, float h, int i, int j, int k, int l,
            *//*?}*/
            @Share("northBoundary")LocalBooleanRef boundary
    ) {
        return boundary.get();
    }

    @WrapWithCondition(
            method = "renderPainting",
            at = {
                    @At(ordinal = 12, value = "INVOKE", target = vertexTarget),
                    @At(ordinal = 13, value = "INVOKE", target = vertexTarget),
                    @At(ordinal = 14, value = "INVOKE", target = vertexTarget),
                    @At(ordinal = 15, value = "INVOKE", target = vertexTarget)
            }
    )
    private boolean preventSouthFrame(
            PaintingRenderer instance,
            /*? if >1.20.2 {*/
            PoseStack.Pose pose, VertexConsumer vertexConsumer, float f, float g, float h, float i, float j, int k, int l, int m, int n,
            /*? } elif >1.19.2 {*//*
            Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer vertexConsumer, float f, float g, float u, float v, float h, int i, int j, int k, int lightmapUV,
            *//*? } else {*//*
            Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer vertexConsumer, float f, float g, float u, float v, float h, int i, int j, int k, int l,
            *//*?}*/
            @Share("southBoundary")LocalBooleanRef boundary
    ) {
        return boundary.get();
    }

    @WrapWithCondition(
            method = "renderPainting",
            at = {
                    @At(ordinal = 16, value = "INVOKE", target = vertexTarget),
                    @At(ordinal = 17, value = "INVOKE", target = vertexTarget),
                    @At(ordinal = 18, value = "INVOKE", target = vertexTarget),
                    @At(ordinal = 19, value = "INVOKE", target = vertexTarget)
            }
    )
    private boolean preventWestFrame(
            PaintingRenderer instance,
            /*? if >1.20.2 {*/
            PoseStack.Pose pose, VertexConsumer vertexConsumer, float f, float g, float h, float i, float j, int k, int l, int m, int n,
            /*? } elif >1.19.2 {*//*
            Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer vertexConsumer, float f, float g, float u, float v, float h, int i, int j, int k, int lightmapUV,
            *//*? } else {*//*
            Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer vertexConsumer, float f, float g, float u, float v, float h, int i, int j, int k, int l,
            *//*?}*/
            @Share("westBoundary")LocalBooleanRef boundary
    ) {
        return boundary.get();
    }

    @WrapWithCondition(
            method = "renderPainting",
            at = {
                    @At(ordinal = 20, value = "INVOKE", target = vertexTarget),
                    @At(ordinal = 21, value = "INVOKE", target = vertexTarget),
                    @At(ordinal = 22, value = "INVOKE", target = vertexTarget),
                    @At(ordinal = 23, value = "INVOKE", target = vertexTarget)
            }
    )
    private boolean preventEastFrame(
            PaintingRenderer instance,
            /*? if >1.20.2 {*/
            PoseStack.Pose pose, VertexConsumer vertexConsumer, float f, float g, float h, float i, float j, int k, int l, int m, int n,
            /*? } elif >1.19.2 {*//*
            Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer vertexConsumer, float f, float g, float u, float v, float h, int i, int j, int k, int lightmapUV,
            *//*? } else {*//*
            Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer vertexConsumer, float f, float g, float u, float v, float h, int i, int j, int k, int l,
            *//*?}*/
            @Share("eastBoundary")LocalBooleanRef boundary
    ) {
        return boundary.get();
    }
}
