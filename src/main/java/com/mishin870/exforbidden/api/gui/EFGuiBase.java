package com.mishin870.exforbidden.api.gui;

import java.util.ArrayList;
import java.util.List;

import com.mishin870.exforbidden.api.SessionVars;
import com.mishin870.exforbidden.api.gui.widgets.ErrorWidget;
import com.mishin870.exforbidden.api.gui.widgets.SimpleWidget;
import com.mishin870.exforbidden.api.gui.widgets.Widget;

import forestry.api.core.IErrorSource;
import forestry.api.core.IErrorState;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;

public abstract class EFGuiBase extends GuiContainer {
	protected WidgetManager widgetManager = new WidgetManager(this);
	
	protected TileEntity tile;
	public EFGuiBase(Container container, IInventory inventory, int xSize, int ySize) {
		super(container);
		this.xSize = xSize;
		this.ySize = ySize;
		if (inventory instanceof TileEntity) tile = (TileEntity) inventory;
		if (inventory instanceof IErrorSource) widgetManager.errorSource = (IErrorSource) inventory;
		initWidgers(inventory);
		widgetManager.initErrors();
		widgetManager.lastDraw = System.currentTimeMillis();
	}
	
	protected void addSimpleWidget(String key, boolean isRight, IIcon icon) {
		this.widgetManager.add(new SimpleWidget(this, "efwidget." + key, isRight, icon));
	}
	
	protected void initWidgers(IInventory inventory) {}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		widgetManager.drawWidgets(mouseX, mouseY);
	}
	
	protected int getCenteredOffset(String string) {
		return getCenteredOffset(string, xSize);
	}
	
	protected int getCenteredOffset(String string, int xWidth) {
		return (xWidth - mc.fontRenderer.getStringWidth(string)) / 2;
	}
	
	@Override
	protected void mouseClicked(int par1, int par2, int mouseButton) {
		super.mouseClicked(par1, par2, mouseButton);
		widgetManager.handleMouseClicked(par1, par2, mouseButton);
	}
	
	private int mouseX = 0;
	private int mouseY = 0;
	
	@Override
	protected void mouseMovedOrUp(int i, int j, int k) {
		super.mouseMovedOrUp(i, j, k);
		mouseX = i;
		mouseY = j;
	}
	
	//==============================WIDGET MANAGER=================================
	protected class WidgetManager {
		public IErrorSource errorSource;
		private EFGuiBase gui;
		private long lastDraw;
		protected ArrayList<Widget> widgets = new ArrayList<Widget>();
		protected ArrayList<Widget> leftWidgets = new ArrayList<Widget>();
		protected ArrayList<ErrorWidget> errorWidgets = new ArrayList<ErrorWidget>();
		
		public WidgetManager(EFGuiBase gui) {
			this.gui = gui;
		}
		
		public void initErrors() {
			int max = (this.gui.ySize - 10) / 24;
			for (int i = 0; i < max; i++) errorWidgets.add(new ErrorWidget(this.gui));
		}
		
		public void add(Widget widget) {
			if (widget.getIsRight()) {
				this.widgets.add(widget);
			} else {
				this.leftWidgets.add(widget);
			}
			if (SessionVars.getOpenedWidget() != null && widget.getClass().equals(SessionVars.getOpenedWidget()) &&
					widget.getKey().equals(SessionVars.getOpenedWidgetKey())) widget.setFullyOpen();
		}
		
		private Widget getAtPosition(int mX, int mY) {
			int xShift, yShift;
			if (this.widgets.size() > 0) {
				xShift = (this.gui.width - this.gui.xSize) / 2 + this.gui.xSize;
				yShift = (this.gui.height - this.gui.ySize) / 2 + 8;
				for (Widget widget : this.widgets) {
					if (widget.isVisible()) {
						widget.currentShiftX = xShift;
						widget.currentShiftY = yShift;
						if (widget.intersectsWith(mX, mY)) return widget;
						yShift += widget.getHeight();
					}
				}
			}
			
			int xShiftLeft = (this.gui.width - this.gui.xSize) / 2;
			int yShiftLeft = (this.gui.height - this.gui.ySize) / 2 + 8;
			for (ErrorWidget errorWidger : this.errorWidgets) {
				if (errorWidger.isVisible()) {
					errorWidger.currentShiftX = (xShiftLeft - errorWidger.getWidth());
					errorWidger.currentShiftY = yShiftLeft;
					if (errorWidger.intersectsWith(mX, mY)) return errorWidger;
					yShiftLeft += errorWidger.getHeight();
				}
			}
			
			//xShiftLeft = (this.gui.width - this.gui.xSize) / 2;
			//yShiftLeft = (this.gui.height - this.gui.ySize) / 2 + 8;
			for (Widget leftWidget : this.leftWidgets) {
				if (leftWidget.isVisible()) {
					leftWidget.currentShiftX = (xShiftLeft - leftWidget.getWidth());
					leftWidget.currentShiftY = yShiftLeft;
					if (leftWidget.intersectsWith(mX, mY)) return leftWidget;
					yShiftLeft += leftWidget.getHeight();
				}
			}
			return null;
		}
		
		public void drawWidgets(int mx, int my) {
			long now = System.currentTimeMillis();
			long frames = (now - lastDraw) / 8;
			lastDraw = now;
			
			int yPos = 8;
			for (Widget widget : this.widgets) {
				widget.update(frames);
				if (widget.isVisible()) {
					//GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					widget.draw(this.gui.xSize, yPos);
					yPos += widget.getHeight();
				}
			}
			
			if (this.errorSource != null) {
				Object errorStates = new ArrayList(this.errorSource.getErrorStates());
				yPos = 8;
				int ind = 0;
				for (ErrorWidget widget : this.errorWidgets) {
					if (ind >= ((List) errorStates).size()) {
						widget.setState(null);
					} else {
						IErrorState errorState = (IErrorState) ((List) errorStates).get(ind++);
						widget.setState(errorState);
						widget.update(frames);
						if (widget.isVisible()) {
							//GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
							widget.draw(-widget.getWidth(), yPos);
							yPos += widget.getHeight();
						}
					}
				}
			}
			
			//yPos = 8;
			for (Widget leftWidget : this.leftWidgets) {
				leftWidget.update(frames);
				if (leftWidget.isVisible()) {
					//GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					leftWidget.draw(-leftWidget.getWidth(), yPos);
					yPos += leftWidget.getHeight();
				}
			}
			
			Widget widget = getAtPosition(mx, my);
			if (widget != null) {
				int startX = mx - ((gui.width - gui.xSize) / 2) + 12;
				int startY = my - ((gui.height - gui.ySize) / 2) - 12;
				String tooltip = widget.getTooltip();
				int textWidth = mc.fontRenderer.getStringWidth(tooltip);
				drawGradientRect(startX - 3, startY - 3, startX + textWidth + 3, startY + 8 + 3, 0xc0000000, 0xc0000000);
				mc.fontRenderer.drawStringWithShadow(tooltip, startX, startY, -1);
			}
		}
		
		public void handleMouseClicked(int x, int y, int mouseButton) {
			if (mouseButton == 0) {
				Widget widget = this.getAtPosition(x, y);
				//Скрытие-открытие только если Widget не обрабатывает нажатие (кнопка внутри Widget)
				if (widget != null && !widget.handleMouseClicked(x, y, mouseButton)) {
					for (Widget other : widgets) if (other != widget && other.isOpen()) other.toggleOpen();
					for (ErrorWidget other : errorWidgets) if (other != widget && other.isOpen()) other.toggleOpen();
					for (Widget other : leftWidgets) if (other != widget && other.isOpen()) other.toggleOpen();
					widget.toggleOpen();
				}
			}
			
		}
		
	}
	
}