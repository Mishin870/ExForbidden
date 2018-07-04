package com.mishin870.exforbidden.forestrycomp.transdim_apiary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.ImmutableSet;
import com.mishin870.exforbidden.api.ICVisContainer;
import com.mishin870.exforbidden.api.gui.widgets.IClimateProvider;
import com.mishin870.exforbidden.api.items.IBiomeStabilizer;
import com.mishin870.exforbidden.forestrycomp.EFErrorLogic;
import com.mishin870.exforbidden.forestrycomp.EFForestryErrors;
import com.mishin870.exforbidden.net.NetUtils;
import com.mishin870.exforbidden.net.PacketTransdimensionalApiaryChargeUpdate;
import com.mishin870.exforbidden.utils.ItemUtils;
import com.mojang.authlib.GameProfile;

import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.DefaultBeeListener;
import forestry.api.apiculture.DefaultBeeModifier;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeHousingInventory;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.apiculture.IBeekeepingLogic;
import forestry.api.apiculture.IHiveFrame;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.core.IErrorLogic;
import forestry.api.core.IErrorSource;
import forestry.api.core.IErrorState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.visnet.VisNetHandler;

public class TileEntityTransdimensionalApiary extends TileEntity implements ICVisContainer, IClimateProvider, ISidedInventory,
IBeeHousing, IErrorSource {
	private static final int TICK_RATE_DRAIN = 5;
	private static final int TICK_RATE_CONSUME = 10;
	private static final int MAX_VIS = 50;
	private int[] charges;
	private boolean hasVis, hasStabilizer;
	private BiomeGenBase defaultBiome;
	private GameProfile ownerProfile;
	private int breedingProgressPercent = 0;
	private final IBeekeepingLogic beeLogic;
	private final IErrorLogic errorLogic;
	private final IBeeListener beeListener;
	private final IBeeModifier beeModifier;
	private final TransdimensionalApiaryInventory inventory;
	
	public TileEntityTransdimensionalApiary() {
		charges = new int[] {0, 0, 0, 0, 0, 0};
		this.beeLogic = BeeManager.beeRoot.createBeekeepingLogic(this);
		this.beeModifier = new TransdimensionalApiaryBeeModifier(this);
		this.beeListener = new TransdimensionalApiaryBeeListener(this);
		this.inventory = new TransdimensionalApiaryInventory(this);
		this.errorLogic = new EFErrorLogic();
	}
	
	public void setCharges(int[] charges) {
		this.charges = charges;
	}
	
	@Override
	public Iterable<IBeeModifier> getBeeModifiers() {
		List<IBeeModifier> beeModifiers = new ArrayList();
		beeModifiers.add(this.beeModifier);
		return beeModifiers;
	}
	
	@Override
	public Iterable<IBeeListener> getBeeListeners() {
		return Collections.singleton(this.beeListener);
	}
	
	@Override
	public IBeeHousingInventory getBeeInventory() {
		return this.inventory;
	}
	
	@Override
	public IBeekeepingLogic getBeekeepingLogic() {
		return this.beeLogic;
	}
	
	public void setOwner(EntityPlayer player) {
		this.ownerProfile = player.getGameProfile();
	}
	
	@Override
	public IErrorLogic getErrorLogic() {
		return this.errorLogic;
	}
	
	@Override
	public GameProfile getOwner() {
		return this.ownerProfile;
	}
	
	@Override
	public World getWorld() {
		return this.worldObj;
	}
	
	@Override
	public ChunkCoordinates getCoordinates() {
		return new ChunkCoordinates(this.xCoord, this.yCoord, this.zCoord);
	}
	
	@Override
	public Vec3 getBeeFXCoordinates() {
		return Vec3.createVectorHelper(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D);
	}
	
	@Override
	public BiomeGenBase getBiome() {
		ItemStack stabilizer = inventory.getStackInSlot(TransdimensionalApiaryInventory.SLOT_BIOME_STABILIZER);
		if (stabilizer != null) {
			IBiomeStabilizer ibs = (IBiomeStabilizer) stabilizer.getItem();
			return ibs.getBiome(stabilizer);
		} else {
			if (this.defaultBiome == null) {
				this.defaultBiome = this.worldObj.getBiomeGenForCoordsBody(this.xCoord, this.zCoord);
			}
			return defaultBiome;
		}
	}
	
	@Override
	public EnumTemperature getTemperature() {
		return EnumTemperature.getFromBiome(getBiome(), this.xCoord, this.yCoord, this.zCoord);
	}
	
	@Override
	public EnumHumidity getHumidity() {
		return EnumHumidity.getFromValue(getExactHumidity());
	}
	
	@Override
	public int getBlockLightValue() {
		return this.worldObj.getBlockLightValue(this.xCoord, this.yCoord + 1, this.zCoord);
	}
	
	@Override
	public boolean canBlockSeeTheSky() {
		return this.worldObj.canBlockSeeTheSky(this.xCoord, this.yCoord + 1, this.zCoord);
	}
	
	@Override
	public int getSizeInventory() {
		return this.inventory.getSizeInventory();
	}
	
	@Override
	public ItemStack getStackInSlot(int i) {
		return this.inventory.getStackInSlot(i);
	}
	
	@Override
	public ItemStack decrStackSize(int i, int j) {
		ItemStack itemStack = getStackInSlot(i);
		if (itemStack != null) {
			if (itemStack.stackSize <= j) {
				setInventorySlotContents(i, null);
			} else {
				itemStack = itemStack.splitStack(j);
				markDirty();
			}
		}
		return itemStack;
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		ItemStack item = getStackInSlot(i);
		setInventorySlotContents(i, null);
		return item;
	}
	
	@Override
	public void setInventorySlotContents(int i, ItemStack itemStack) {
		this.inventory.setInventorySlotContents(i, itemStack);
		markDirty();
	}
	
	@Override
	public String getInventoryName() {
		return "efgui.transdimensional_apiary";
	}
	
	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}
	
	@Override
	public int getInventoryStackLimit() {
		return this.inventory.getInventoryStackLimit();
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
		return entityPlayer.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
	}
	
	@Override
	public void openInventory() {}
	
	@Override
	public void closeInventory() {}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return true;
	}
	
	public int getHealthScaled(int i) {
		return this.breedingProgressPercent * i / 100;
	}
	
	public int getTemperatureScaled(int i) {
		return Math.round(i * (getExactTemperature() / 2.0F));
	}
	
	public int getHumidityScaled(int i) {
		return Math.round(i * getExactHumidity());
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		this.inventory.writeToNBT(compound);
		this.beeLogic.writeToNBT(compound);
		compound.setIntArray("charges", charges);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.inventory.readFromNBT(compound);
		this.beeLogic.readFromNBT(compound);
		this.charges = compound.getIntArray("charges");
	}
	
	@Override
	public Packet getDescriptionPacket() {
		this.beeLogic.syncToClient();
		PacketTransdimensionalApiaryChargeUpdate packet = new PacketTransdimensionalApiaryChargeUpdate(charges, xCoord, yCoord, zCoord);
		return packet.getPacket();
	}
	
	public float getExactTemperature() {
		return getBiome().getFloatTemperature(this.xCoord, this.yCoord, this.zCoord);
	}
	
	public float getExactHumidity() {
		return getBiome().rainfall;
	}
	
	@Override
	public void updateEntity() {
		if (this.worldObj.isRemote) {
			updateClientSide();
		} else {
			updateServerSide();
		}
	}
	
	public void updateClientSide() {
		if ((this.beeLogic.canDoBeeFX()) && (this.worldObj.getTotalWorldTime() % 10L == 0L)) {
			this.beeLogic.doBeeFX();
		}
	}
	
	public void updateServerSide() {
		//потребление сантивис
		long tick = this.worldObj.getTotalWorldTime();
		if (tick % TICK_RATE_DRAIN == 0) {
			charges[0] += VisNetHandler.drainVis(this.worldObj, this.xCoord, this.yCoord, this.zCoord, Aspect.WATER, MAX_VIS - charges[0]);
			charges[1] += VisNetHandler.drainVis(this.worldObj, this.xCoord, this.yCoord, this.zCoord, Aspect.FIRE, MAX_VIS - charges[1]);
			charges[2] += VisNetHandler.drainVis(this.worldObj, this.xCoord, this.yCoord, this.zCoord, Aspect.EARTH, MAX_VIS - charges[2]);
			charges[3] += VisNetHandler.drainVis(this.worldObj, this.xCoord, this.yCoord, this.zCoord, Aspect.AIR, MAX_VIS - charges[3]);
			charges[4] += VisNetHandler.drainVis(this.worldObj, this.xCoord, this.yCoord, this.zCoord, Aspect.ORDER, MAX_VIS - charges[4]);
			charges[5] += VisNetHandler.drainVis(this.worldObj, this.xCoord, this.yCoord, this.zCoord, Aspect.ENTROPY, MAX_VIS - charges[5]);
		}
		if (tick % TICK_RATE_CONSUME == 0) {
			hasVis = true;
			ItemStack itemStack = inventory.getStackInSlot(TransdimensionalApiaryInventory.SLOT_BIOME_STABILIZER);
			if (itemStack != null) {
				Item item = itemStack.getItem();
				if (item instanceof IBiomeStabilizer) {
					AspectList list = ((IBiomeStabilizer) item).getPrice(itemStack);
					Aspect[] arr = list.getAspects();
					for (int i = 0; i < 6; i++) {
						charges[i] -= list.getAmount(arr[i]);
						if (charges[i] < 0) {
							charges[i] = 0;
							hasVis = false;
						}
					}
					hasStabilizer = true;
				} else {
					hasStabilizer = false;
				}
			} else {
				hasStabilizer = false;
			}
			PacketTransdimensionalApiaryChargeUpdate packet = new PacketTransdimensionalApiaryChargeUpdate(charges, this.xCoord, this.yCoord, this.zCoord);
			NetUtils.sendNetworkPacket(packet, this.xCoord, this.zCoord, this.worldObj);
		}
		//работа
		if (this.beeLogic.canWork() && hasStabilizer && hasVis) {
			this.beeLogic.doWork();
		}
		this.errorLogic.setCondition(!hasVis, EFForestryErrors.NO_VIS);
		this.errorLogic.setCondition(!hasStabilizer, EFForestryErrors.NO_STABILIZER);
	}
	
	public void getGUINetworkData(int i, int j) {
		switch (i) {
			case 0:
				this.breedingProgressPercent = j;
		}
	}
	
	public void sendGUINetworkData(Container container, ICrafting iCrafting) {
		iCrafting.sendProgressBarUpdate(container, 0, this.beeLogic.getBeeProgressPercent());
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int i) {
		return this.inventory.getAccessibleSlotsFromSide(i);
	}
	
	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int i1) {
		return this.inventory.canInsertItem(i, itemStack, i1);
	}
	
	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int i1) {
		return this.inventory.canExtractItem(i, itemStack, i1);
	}

	private static class TransdimensionalApiaryInventory implements IBeeHousingInventory {
		public static final int SLOT_QUEEN = 0;
		public static final int SLOT_DRONE = 1;
		public static final int SLOT_BIOME_STABILIZER = 2;
		public static final int SLOT_PRODUCTS_START = 3;
		public static final int SLOT_PRODUCTS_COUNT = 7;
		private final TileEntityTransdimensionalApiary apiary;
		private ItemStack[] items;
		
		public TransdimensionalApiaryInventory(TileEntityTransdimensionalApiary apiary) {
			this.apiary = apiary;
			this.items = new ItemStack[10];
		}
		
		public ItemStack getQueen() {
			return this.apiary.getStackInSlot(0);
		}
		
		public ItemStack getDrone() {
			return this.apiary.getStackInSlot(1);
		}
		
		public void setQueen(ItemStack itemstack) {
			this.apiary.setInventorySlotContents(0, itemstack);
		}
		
		public void setDrone(ItemStack itemstack) {
			this.apiary.setInventorySlotContents(1, itemstack);
		}
		
		public boolean addProduct(ItemStack product, boolean all) {
			int countAdded = ItemUtils.addItemToInventory(this.apiary, product, 3, 7);
			if (all) return countAdded == product.stackSize;
			return countAdded > 0;
		}
		
		public int getSizeInventory() {
			return this.items.length;
		}
		
		public ItemStack getStackInSlot(int i) {
			return this.items[i];
		}
		
		public void setInventorySlotContents(int i, ItemStack itemStack) {
			this.items[i] = itemStack;
			if ((itemStack != null) && (itemStack.stackSize > getInventoryStackLimit())) {
				itemStack.stackSize = getInventoryStackLimit();
			}
		}
		
		public int[] getAccessibleSlotsFromSide(int side) {
			if ((side == 0) || (side == 1)) {
				return new int[] {0, 1};
			}
			int[] slots = new int[SLOT_PRODUCTS_COUNT];
			int i = 0;
			for (int slot = SLOT_PRODUCTS_START; i < SLOT_PRODUCTS_COUNT; slot++) {
				slots[i] = slot;
				i++;
			}
			return slots;
		}
		
		public boolean canInsertItem(int slot, ItemStack itemStack, int side) {
			if ((slot == 0) && (BeeManager.beeRoot.isMember(itemStack)) && (!BeeManager.beeRoot.isDrone(itemStack))) {
				return true;
			} else if ((slot == 1) && (BeeManager.beeRoot.isDrone(itemStack))) {
				return true;
			} else {
				return false;
			}
		}
		public boolean canExtractItem(int slot, ItemStack itemStack, int side) {
			return slot >= 3;
		}
		
		public int getInventoryStackLimit() {
			return 64;
		}
		
		public Collection<IHiveFrame> getFrames() {
			return new ArrayList<IHiveFrame>();
		}
		
		public void writeToNBT(NBTTagCompound compound) {
			NBTTagList itemsNBT = new NBTTagList();
			for (int i = 0; i < this.items.length; i++) {
				ItemStack itemStack = this.items[i];
				if (itemStack != null) {
					NBTTagCompound item = new NBTTagCompound();
					item.setByte("Slot", (byte) i);
					itemStack.writeToNBT(item);
					itemsNBT.appendTag(item);
				}
			}
			compound.setTag("Items", itemsNBT);
		}
		
		public void readFromNBT(NBTTagCompound compound) {
			NBTTagList items = compound.getTagList("Items", 10);
			for (int i = 0; i < items.tagCount(); i++) {
				NBTTagCompound item = items.getCompoundTagAt(i);
				int slot = item.getByte("Slot");
				if ((slot >= 0) && (slot < getSizeInventory())) {
					setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
				}
			}
		}
	}
	
	private static class TransdimensionalApiaryBeeModifier extends DefaultBeeModifier {
		private final TileEntityTransdimensionalApiary apiary;
		
		public TransdimensionalApiaryBeeModifier(TileEntityTransdimensionalApiary apiary) {
			this.apiary = apiary;
		}
		
		@Override
		public float getMutationModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
			return 1.0f;
		}
		
		@Override
		public float getLifespanModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
			return 1.0f;
		}
		
		@Override
		public float getProductionModifier(IBeeGenome genome, float currentModifier) {
			return 0.0f;
		}
		
		@Override
		public float getGeneticDecay(IBeeGenome genome, float currentModifier) {
			return 0.5F;
		}
	}
	
	private static class TransdimensionalApiaryBeeListener extends DefaultBeeListener {
		private final TileEntityTransdimensionalApiary apiary;
		
		public TransdimensionalApiaryBeeListener(TileEntityTransdimensionalApiary apiary) {
			this.apiary = apiary;
		}
		
		@Override
		public void wearOutEquipment(int amount) {
			
		}
	}
	
	@Override
	public ImmutableSet<IErrorState> getErrorStates() {
		return errorLogic.getErrorStates();
	}
	
	@Override
	public int getAqua() {
		return charges[0];
	}
	
	@Override
	public int getIgnis() {
		return charges[1];
	}
	
	@Override
	public int getTerra() {
		return charges[2];
	}
	
	@Override
	public int getAir() {
		return charges[3];
	}
	
	@Override
	public int getOrdo() {
		return charges[4];
	}
	
	@Override
	public int getPerditio() {
		return charges[5];
	}
	
	@Override
	public int getMax() {
		return MAX_VIS;
	}
}