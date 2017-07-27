package com.mishin870.exforbidden.forestrycomp.extra_carpenter;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelExtraCarpenter extends ModelBase {
	ModelRenderer center, right, left, c1, c2, c3, c4;
	
	public ModelExtraCarpenter() {
		textureWidth = 64;
		textureHeight = 32;
		
		center = new ModelRenderer(this, 0, 0);
		center.addBox(0F, 0F, 0F, 10, 8, 10);
		center.setRotationPoint(-5F, 12F, -5F);
		center.setTextureSize(64, 32);
		center.mirror = true;
		setRotation(center, 0F, 0F, 0F);
		right = new ModelRenderer(this, 0, 13);
		right.addBox(0F, 0F, 0F, 16, 16, 3);
		right.setRotationPoint(-8F, 8F, -8F);
		right.setTextureSize(64, 32);
		right.mirror = true;
		setRotation(right, 0F, 0F, 0F);
		left = new ModelRenderer(this, 19, 13);
		left.addBox(0F, 0F, 0F, 16, 16, 3);
		left.setRotationPoint(-8F, 8F, 5F);
		left.setTextureSize(64, 32);
		left.mirror = true;
		setRotation(left, 0F, 0F, 0F);
		c1 = new ModelRenderer(this, 46, 0);
		c1.addBox(0F, 0F, 0F, 2, 6, 7);
		c1.setRotationPoint(-4F, 13F, 0F);
		c1.setTextureSize(64, 32);
		c1.mirror = true;
		setRotation(c1, 0F, -0.5235988F, 0F);
		c2 = new ModelRenderer(this, 46, 0);
		c2.addBox(0F, 0F, 0F, 2, 6, 7);
		c2.setRotationPoint(-7F, 13F, -5F);
		c2.setTextureSize(64, 32);
		c2.mirror = true;
		setRotation(c2, 0F, 0.5235988F, 0F);
		c3 = new ModelRenderer(this, 46, 0);
		c3.addBox(0F, 0F, 0F, 2, 6, 7);
		c3.setRotationPoint(2F, 13F, 1F);
		c3.setTextureSize(64, 32);
		c3.mirror = true;
		setRotation(c3, 0F, 0.5235988F, 0F);
		c4 = new ModelRenderer(this, 46, 0);
		c4.addBox(0F, 0F, 0F, 2, 6, 7);
		c4.setRotationPoint(6F, 13F, -8F);
		c4.setTextureSize(64, 32);
		c4.mirror = true;
		setRotation(c4, 0F, -0.5235988F, 0F);
	}
	
	public void renderAll() {
		center.render(0.0625F);
		left.render(0.0625F);
		right.render(0.0625F);
		c1.render(0.0625F);
		c2.render(0.0625F);
		c3.render(0.0625F);
		c4.render(0.0625F);
	}
	
	private void setRotation(ModelRenderer m, float x, float y, float z) {
		m.rotateAngleX = x;
		m.rotateAngleY = y;
		m.rotateAngleZ = z;
	}
	
}
