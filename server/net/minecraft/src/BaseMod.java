/*
 * The FML Forge Mod Loader suite.
 * Copyright (C) 2012 cpw
 * 
 * This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with this library; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */
package net.minecraft.src;

import java.util.Random;

import fml.Mod;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.World;

public abstract class BaseMod {
  int addFuel(int id, int metadata) {
    return 0;
  }

  public boolean dispenseEntity(World world, double x, double y, double z, int xVel, int zVel, ItemStack item) {
    return false;
  }

  public void generateNether(World world, Random random, int chunkX, int chunkZ) {
  }

  public void generateSurface(World world, Random random, int chunkX, int chunkZ) {
  }

  public String getName() { return ""; }

  public String getPriorities() {
    return null;
  }

  public abstract String getVersion();

  public abstract void load();

  public void modsLoaded() {
  }

  public void onItemPickup(EntityPlayer player, ItemStack item) {
  }

  // boolean onTickInGame(float tick, Minecraft game);
  public boolean onTickInGame(MinecraftServer minecraftServer) {
    return false;
  }

  public void receiveChatPacket(String text) {
  }

  public void receiveCustomPacket(Packet250CustomPayload packet) {
  }

  public void takenFromCrafting(EntityPlayer player, ItemStack item, IInventory matrix) {
  }

  public void takenFromFurnace(EntityPlayer player, ItemStack item) {

  }

  // void addRenderer(Map<Class<? extends Entity>, Render> renderers);
  // void registerAnimation(Minecraft game);
  // void renderInvBlock(RenderBlocks renderer, Block block, int metadata, int modelID);
  // boolean renderWorldBlock(RenderBlocks renderer, IBlockAccess world, int x, int y, int z, Block block, int modelID);
  // boolean onTickInGUI(float tick, Minecraft game, GuiScreen gui);
  // void keyboardEvent(KeyBinding event);
}