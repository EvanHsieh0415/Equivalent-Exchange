package net.creeperhost.equivalentexchange.client.renders;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

public class EntitySpriteRender<ENTITY extends Entity> extends EntityRenderer<ENTITY>
{
    private final ResourceLocation texture;

    public EntitySpriteRender(EntityRendererProvider.Context context, ResourceLocation texture)
    {
        super(context);
        this.texture = texture;
    }

    @NotNull
    @Override
    public ResourceLocation getTextureLocation(@NotNull ENTITY entity)
    {
        return texture;
    }

    @Override
    public void render(@NotNull ENTITY entity, float entityYaw, float partialTick, @NotNull PoseStack matrix, @NotNull MultiBufferSource renderer, int light)
    {
        matrix.pushPose();
        matrix.mulPose(entityRenderDispatcher.cameraOrientation());
        matrix.scale(0.5F, 0.5F, 0.5F);
        VertexConsumer builder = renderer.getBuffer(RenderTypes.SPRITE_RENDERER.apply(getTextureLocation(entity)));
        Matrix4f matrix4f = matrix.last().pose();
        builder.vertex(matrix4f, -1, -1, 0).uv(1, 1).endVertex();
        builder.vertex(matrix4f, -1, 1, 0).uv(1, 0).endVertex();
        builder.vertex(matrix4f, 1, 1, 0).uv(0, 0).endVertex();
        builder.vertex(matrix4f, 1, -1, 0).uv(0, 1).endVertex();
        matrix.popPose();
    }
}
