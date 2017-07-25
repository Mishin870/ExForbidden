package com.mishin870.exforbidden.forestrycomp;

import com.mojang.authlib.GameProfile;

import forestry.api.apiculture.IBeeModifier;
import forestry.api.multiblock.IAlvearyComponent.BeeModifier;
import forestry.api.multiblock.IMultiblockController;
import forestry.api.multiblock.IMultiblockLogicAlveary;
import forestry.api.multiblock.MultiblockManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

public class TileEntityAlvearyBlock extends TileEntity implements BeeModifier {
	public IMultiblockLogicAlveary multiBlockLogic;
	
	public TileEntityAlvearyBlock() {
		multiBlockLogic = MultiblockManager.logicFactory.createAlvearyLogic();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
	}

	@Override
	public ChunkCoordinates getCoordinates() {
		return new ChunkCoordinates(this.xCoord, this.yCoord, this.zCoord);
	}

	@Override
	public IMultiblockLogicAlveary getMultiblockLogic() {
		return multiBlockLogic;
	}

	@Override
	public GameProfile getOwner() {
		return null;
	}
	
	@Override
	public void onMachineAssembled(IMultiblockController controller, ChunkCoordinates arg1, ChunkCoordinates arg2) {
		if (this.worldObj.isRemote) this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, getBlockType());
		markDirty();
		//Main.sendToMe("Custom EF alveary block assembled!");
	}
	
	@Override
	public void onMachineBroken() {
		if (this.worldObj.isRemote) this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, getBlockType());
		markDirty();
		//Main.sendToMe("Custom EF alveary block disassembled!");
	}
	
	@Override
	public final void invalidate() {
		super.invalidate();
		this.multiBlockLogic.invalidate(this.worldObj, this);
	}
	
	@Override
	public final void onChunkUnload() {
		super.onChunkUnload();
		this.multiBlockLogic.onChunkUnload(this.worldObj, this);
	}
	
	@Override
	public final void validate() {
		super.validate();
		this.multiBlockLogic.validate(this.worldObj, this);
	}
	
	@Override
	public final Packet getDescriptionPacket() {
		NBTTagCompound packetData = new NBTTagCompound();
		this.multiBlockLogic.encodeDescriptionPacket(packetData);
		encodeDescriptionPacket(packetData);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, packetData);
	}
	
	public final void onDataPacket(NetworkManager network, S35PacketUpdateTileEntity packet) {
		NBTTagCompound nbtData = packet.func_148857_g();
		this.multiBlockLogic.decodeDescriptionPacket(nbtData);
		decodeDescriptionPacket(nbtData);
	}

	protected void encodeDescriptionPacket(NBTTagCompound packetData) {}
	protected void decodeDescriptionPacket(NBTTagCompound packetData) {}
	
	@Override
	public IBeeModifier getBeeModifier() {
		return null;
	}

}