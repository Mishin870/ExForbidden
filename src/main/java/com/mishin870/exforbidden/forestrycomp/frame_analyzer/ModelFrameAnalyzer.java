package com.mishin870.exforbidden.forestrycomp.frame_analyzer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelFrameAnalyzer extends ModelBase {
	ModelRenderer main, leg1, leg2, leg3, leg4, pedestal, pleg1, pleg2, pleg3, pleg4;
	
	public ModelFrameAnalyzer() {
		textureWidth = 64;
		textureHeight = 32;

		main = new ModelRenderer(this, 0, 12);
		main.addBox(0F, 0F, 0F, 16, 4, 16);
		main.setRotationPoint(-8F, 12F, -8F);
		main.setTextureSize(64, 32);
		main.mirror = true;
		setRotation(main, 0F, 0F, 0F);
		leg1 = new ModelRenderer(this, 0, 0);
		leg1.addBox(0F, 0F, 0F, 2, 7, 2);
		leg1.setRotationPoint(5F, 16F, 5F);
		leg1.setTextureSize(64, 32);
		leg1.mirror = true;
		setRotation(leg1, 0F, 0F, 0F);
		leg2 = new ModelRenderer(this, 0, 0);
		leg2.addBox(0F, 0F, 0F, 2, 7, 2);
		leg2.setRotationPoint(5F, 16F, -7F);
		leg2.setTextureSize(64, 32);
		leg2.mirror = true;
		setRotation(leg2, 0F, 0F, 0F);
		leg3 = new ModelRenderer(this, 0, 0);
		leg3.addBox(0F, 0F, 0F, 2, 7, 2);
		leg3.setRotationPoint(-7F, 16F, 5F);
		leg3.setTextureSize(64, 32);
		leg3.mirror = true;
		setRotation(leg3, 0F, 0F, 0F);
		leg4 = new ModelRenderer(this, 0, 0);
		leg4.addBox(0F, 0F, 0F, 2, 7, 2);
		leg4.setRotationPoint(-7F, 16F, -7F);
		leg4.setTextureSize(64, 32);
		leg4.mirror = true;
		setRotation(leg4, 0F, 0F, 0F);
		pedestal = new ModelRenderer(this, 8, 0);
		pedestal.addBox(-7F, 0F, -7F, 14, 1, 14);
		pedestal.setRotationPoint(0F, 11F, 0F);
		pedestal.setTextureSize(64, 32);
		pedestal.mirror = true;
		setRotation(pedestal, 0F, 0.0698132F, 0F);
		pleg1 = new ModelRenderer(this, 0, 0);
		pleg1.addBox(0F, 0F, 0F, 4, 1, 4);
		pleg1.setRotationPoint(-8F, 23F, 4F);
		pleg1.setTextureSize(64, 32);
		pleg1.mirror = true;
		setRotation(pleg1, 0F, 0F, 0F);
		pleg2 = new ModelRenderer(this, 0, 0);
		pleg2.addBox(0F, 0F, 0F, 4, 1, 4);
		pleg2.setRotationPoint(4F, 23F, 4F);
		pleg2.setTextureSize(64, 32);
		pleg2.mirror = true;
		setRotation(pleg2, 0F, 0F, 0F);
		pleg3 = new ModelRenderer(this, 0, 0);
		pleg3.addBox(0F, 0F, 0F, 4, 1, 4);
		pleg3.setRotationPoint(4F, 23F, -8F);
		pleg3.setTextureSize(64, 32);
		pleg3.mirror = true;
		setRotation(pleg3, 0F, 0F, 0F);
		pleg4 = new ModelRenderer(this, 0, 0);
		pleg4.addBox(0F, 0F, 0F, 4, 1, 4);
		pleg4.setRotationPoint(-8F, 23F, -8F);
		pleg4.setTextureSize(64, 32);
		pleg4.mirror = true;
		setRotation(pleg4, 0F, 0F, 0F);
	}
	
	public void renderAll() {
		main.render(0.0625F);
		leg1.render(0.0625F);
		leg2.render(0.0625F);
		leg3.render(0.0625F);
		leg4.render(0.0625F);
		pedestal.render(0.0625F);
		pleg1.render(0.0625F);
		pleg2.render(0.0625F);
		pleg3.render(0.0625F);
		pleg4.render(0.0625F);
	}
	
	private void setRotation(ModelRenderer m, float x, float y, float z) {
		m.rotateAngleX = x;
		m.rotateAngleY = y;
		m.rotateAngleZ = z;
	}
	
}
