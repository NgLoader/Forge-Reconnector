package de.ngloader.reconnector.gui;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;

public class DisconnectedScreen extends GuiScreen {

	private long reconnectingIn;
	private ServerData serverData;

	private boolean destoryed = false;

	public DisconnectedScreen(ServerData serverData) {
		this.serverData = serverData;
		this.reconnectingIn = System.currentTimeMillis() + 5000;
	}

	public void tick() {
		if(reconnectingIn < System.currentTimeMillis() && !destoryed) {
			this.destoryed = true;
			mc.displayGuiScreen(new GuiConnecting(this, this.mc, this.serverData));
		}
	}

	@Override
	public void initGui() {
		this.buttonList.clear();
		this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 80, "Stop"));
		this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 106, "Reconnect"));
		this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 132, I18n.format("gui.toMenu")));
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		this.destoryed = true;
		switch (button.id) {
		case 0:
			button.displayString = "Stopped";
			button.enabled = false;
			break;

		case 1:
			mc.displayGuiScreen(new GuiConnecting(this, this.mc, this.serverData));
			break;

		case 2:
			mc.displayGuiScreen(new GuiMultiplayer(new GuiMainMenu()));
			break;
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer,
				this.destoryed ? "Reconnecting stopped" : "Reconnecting in " + ((int) ((this.reconnectingIn - System.currentTimeMillis()) / 1000)), this.width / 2, this.height / 2 - 50, 11184810);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}