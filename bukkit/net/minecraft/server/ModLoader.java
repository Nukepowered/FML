/*
 * The FML Forge Mod Loader suite. Copyright (C) 2012 cpw
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
package net.minecraft.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.security.DigestException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.logging.Logger;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ReflectionHelper;
import cpw.mods.fml.common.modloader.ModLoaderHelper;
import cpw.mods.fml.common.modloader.ModLoaderModContainer;
import cpw.mods.fml.common.registry.FMLRegistry;
import cpw.mods.fml.server.FMLBukkitHandler;

public class ModLoader
{
    /**
     * Not used on the server.
     *
     * @param achievement
     * @param name
     * @param description
     */
    public static void addAchievementDesc(Achievement achievement, String name, String description)
    {
        String achName=achievement.getName();
        addLocalization(achName, name);
        addLocalization(achName+".desc", description);
    }

    /**
     * This method is a call in hook from modified external code. Implemented elsewhere.
     *
     * {@link FMLCommonHandler#fuelLookup(int, int)}
     * 
     * @param id
     * @param metadata
     * @return
     */
    @Deprecated
    public static int addAllFuel(int id, int metadata)
    {
        return 0;
    }

    public static void addAllRenderers(Map<Class<? extends Entity>, Object> renderers)
    {
    }

    public static void addAnimation(Object anim)
    {
    }

    /**
     * This method is unimplemented in server versions to date.
     *
     * @param armor
     * @return
     */
    public static int addArmor(String armor)
    {
        return 0;
    }

    /**
     * This method adds the supplied biome to the set of candidate biomes for the default world generator type.
     *
     * @param biome
     */
    public static void addBiome(BiomeBase biome)
    {
        FMLRegistry.addBiome(biome);
    }

    /**
     * Add localization for the specified string
     *
     * @param key
     * @param value
     */
    public static void addLocalization(String key, String value)
    {
        addLocalization(key, "en_US", value);
    }

    /**
     * Add localization for the specified string
     *
     * @param key
     * @param lang
     * @param value
     */
    public static void addLocalization(String key, String lang, String value)
    {
        FMLCommonHandler.instance().addStringLocalization(key, lang, value);
    }

    /**
     * Name the specified minecraft object with the supplied name
     *
     * @param instance
     * @param name
     */
    public static void addName(Object instance, String name)
    {
        addName(instance,"en_US",name);
    }

    /**
     * Unimplemented on the server as it does not generate names
     *
     * @param instance
     * @param lang
     * @param name
     */
    public static void addName(Object instance, String lang, String name)
    {
        FMLCommonHandler.instance().addNameForObject(instance, lang, name);
    }

    /**
     * Unimplemented on the server as it does not render textures
     * 
     * @param fileToOverride
     * @param fileToAdd
     * @return
     */
    public static int addOverride(String fileToOverride, String fileToAdd)
    {
        return 0;
    }

    /**
     * Unimplemented on the server as it does not render textures
     * 
     * @param path
     * @param overlayPath
     * @param index
     */
    public static void addOverride(String path, String overlayPath, int index)
    {
        // NOOP
    }

    /**
     * Add a Shaped Recipe
     *
     * @param output
     * @param params
     */
    public static void addRecipe(ItemStack output, Object... params)
    {
        FMLRegistry.addRecipe(output, params);
    }

    /**
     * Add a shapeless recipe
     *
     * @param output
     * @param params
     */
    public static void addShapelessRecipe(ItemStack output, Object... params)
    {
        FMLRegistry.addShapelessRecipe(output, params);
    }

    /**
     * Add a new product to be smelted
     *
     * @param input
     * @param output
     */
    public static void addSmelting(int input, ItemStack output)
    {
        FMLRegistry.addSmelting(input, output);
    }

    /**
     * Add a mob to the spawn list
     *
     * @param entityClass
     * @param weightedProb
     * @param min
     * @param max
     * @param spawnList
     */
    public static void addSpawn(Class <? extends EntityLiving > entityClass, int weightedProb, int min, int max, EnumCreatureType spawnList)
    {
        FMLRegistry.addSpawn(entityClass, weightedProb, min, max, spawnList, FMLBukkitHandler.instance().getDefaultOverworldBiomes());
    }

    /**
     * Add a mob to the spawn list
     * @param entityClass
     * @param weightedProb
     * @param min
     * @param max
     * @param spawnList
     * @param biomes
     */
    public static void addSpawn(Class <? extends EntityLiving > entityClass, int weightedProb, int min, int max, EnumCreatureType spawnList, BiomeBase... biomes)
    {
        FMLRegistry.addSpawn(entityClass, weightedProb, min, max, spawnList, biomes);
    }

    /**
     * Add a mob to the spawn list
     *
     * @param entityName
     * @param weightedProb
     * @param min
     * @param max
     * @param spawnList
     */
    public static void addSpawn(String entityName, int weightedProb, int min, int max, EnumCreatureType spawnList)
    {
        FMLRegistry.addSpawn(entityName, weightedProb, min, max, spawnList, FMLBukkitHandler.instance().getDefaultOverworldBiomes());
    }

    /**
     * Add a mob to the spawn list
     * @param entityName
     * @param weightedProb
     * @param min
     * @param max
     * @param spawnList
     * @param biomes
     */
    public static void addSpawn(String entityName, int weightedProb, int min, int max, EnumCreatureType spawnList, BiomeBase... biomes)
    {
        FMLRegistry.addSpawn(entityName, weightedProb, min, max, spawnList, biomes);
    }

    /**
     * This method is a call in hook from modified external code. Implemented elsewhere.
     * {@link FMLBukkitHandler#tryDispensingEntity(World, double, double, double, byte, byte, ItemStack)}
     *
     * @param world
     * @param x
     * @param y
     * @param z
     * @param xVel
     * @param zVel
     * @param item
     * @return
     */
    @Deprecated
    public static boolean dispenseEntity(World world, double x, double y, double z, int xVel, int zVel, ItemStack item)
    {
        return false;
    }

    /**
     * Remove a container and drop all the items in it on the ground around
     *
     * @param world
     * @param x
     * @param y
     * @param z
     */
    public static void genericContainerRemoval(World world, int x, int y, int z)
    {
        TileEntity te = world.getTileEntity(x, y, z);

        if (!(te instanceof IInventory))
        {
            return;
        }

        IInventory inv = (IInventory)te;

        for (int l = 0; l < inv.getSize(); l++)
        {
            ItemStack itemstack = inv.getItem(l);

            if (itemstack == null)
            {
                continue;
            }

            float f = world.random.nextFloat() * 0.8F + 0.1F;
            float f1 = world.random.nextFloat() * 0.8F + 0.1F;
            float f2 = world.random.nextFloat() * 0.8F + 0.1F;

            while (itemstack.count > 0)
            {
                int i1 = world.random.nextInt(21) + 10;

                if (i1 > itemstack.count)
                {
                    i1 = itemstack.count ;
                }

                itemstack.count  -= i1;
                EntityItem entityitem = new EntityItem(world, (float)te.x + f, (float)te.y + f1, (float)te.z + f2, new ItemStack(itemstack.id, i1, itemstack.getData()));
                float f3 = 0.05F;
                entityitem.motX = (float)world.random.nextGaussian() * f3;
                entityitem.motY = (float)world.random.nextGaussian() * f3 + 0.2F;
                entityitem.motZ = (float)world.random.nextGaussian() * f3;

                if (itemstack.hasTag())
                {
                    entityitem.itemStack.setTag((NBTTagCompound)itemstack.getTag().clone());
                }

                world.addEntity(entityitem);
            }
        }
    }

    /**
     * Get a list of all BaseMod loaded into the system
     * {@link ModLoaderModContainer#findAll}
     *
     * @return
     */
    public static List<BaseMod> getLoadedMods()
    {
        return ModLoaderModContainer.findAll(BaseMod.class);
    }

    /**
     * Get a logger instance {@link FMLCommonHandler#getFMLLogger()}
     *
     * @return
     */
    public static Logger getLogger()
    {
        return FMLCommonHandler.instance().getFMLLogger();
    }

    public static Object getMinecraftInstance()
    {
        return getMinecraftServerInstance();
    }

    /**
     * Get the minecraft server instance
     * {@link FMLServerHandler#getServer()}
     * @return
     */
    public static MinecraftServer getMinecraftServerInstance()
    {
        return FMLBukkitHandler.instance().getServer();
    }
    /**
     * Get a value from a field using reflection
     * {@link ReflectionHelper#getPrivateValue(Class, Object, int)}
     *
     * @param instanceclass
     * @param instance
     * @param fieldindex
     * @return
     */
    public static <T, E> T getPrivateValue(Class <? super E > instanceclass, E instance, int fieldindex)
    {
        return ReflectionHelper.getPrivateValue(instanceclass, instance, fieldindex);
    }

    /**
     * Get a value from a field using reflection
     * {@link ReflectionHelper#getPrivateValue(Class, Object, String)}
     * 
     * @param instanceclass
     * @param instance
     * @param field
     * @return
     */
    public static <T, E> T getPrivateValue(Class <? super E > instanceclass, E instance, String field)
    {
        return ReflectionHelper.getPrivateValue(instanceclass, instance, field);
    }

    /**
     * Stubbed method on the server to return a unique model id
     * 
     */
    public static int getUniqueBlockModelID(BaseMod mod, boolean inventoryRenderer)
    {
        return -1;
    }

    /**
     * Get a new unique entity id
     * {@link Entity#getNextId()}
     *
     * @return
     */
    public static int getUniqueEntityId()
    {
        return FMLCommonHandler.instance().nextUniqueEntityListId();
    }

    public static int getUniqueSpriteIndex(String path)
    {
        return -1;
    }
    
    /**
     * To properly implement packet 250 protocol you should always check your
     * channel is active prior to sending the packet
     * 
     * @param player
     * @param channel
     * @return
     */
    public static boolean isChannelActive(EntityPlayer player, String channel)
    {
        return FMLCommonHandler.instance().isChannelActive(channel, player);
    }

    public static boolean isGUIOpen(Class<?> gui)
    {
        return false;
    }

    /**
     * Is the named mod loaded?
     * {@link Loader#isModLoaded(String)}
     * 
     * @param modname
     * @return
     */
    public static boolean isModLoaded(String modname)
    {
        return Loader.isModLoaded(modname);
    }

    /**
     * Implemented elsewhere
     */
    @Deprecated
    public static void loadConfig()
    {
    }

    public static Object loadImage(Object renderEngine, String path) throws Exception
    {
        return null;
    }

    /**
     * Call in from elsewhere. Unimplemented here.
     * @param player
     * @param item
     */
    @Deprecated
    public static void onItemPickup(EntityPlayer player, ItemStack item)
    {
    }
    /**
     * Call in from elsewhere. Unimplemented here.
     */
    @Deprecated
    public static void onTick(float tick, Object game)
    {
    }

    public static void openGUI(EntityPlayer player, Object gui)
    {
        // NOOP
    }

    @Deprecated
    public static void populateChunk(IChunkProvider generator, int chunkX, int chunkZ, World world)
    {
    }

    /**
     * This method is a call in hook from modified external code. Implemented elsewhere.
     * {@link FMLBukkitHandler#handlePacket250(Packet250CustomPayload, EntityHuman)}
     *
     * @param packet
     */
    @Deprecated
    public static void receivePacket(Packet250CustomPayload packet)
    {
    }

    @Deprecated
    public static Object[] registerAllKeys(Object[] keys)
    {
        return keys;
    }

    @Deprecated
    public static void registerAllTextureOverrides(Object cache)
    {
    }

    /**
     * Register a new block
     * 
     * @param block
     */
    public static void registerBlock(Block block)
    {
        FMLRegistry.registerBlock(block);
    }

    /**
     * Register a new block
     * 
     * @param block
     * @param itemclass
     */
    public static void registerBlock(Block block, Class <? extends ItemBlock > itemclass)
    {
        FMLRegistry.registerBlock(block, itemclass);
    }

    /**
     * Register a new entity ID
     * 
     * @param entityClass
     * @param entityName
     * @param id
     */
    public static void registerEntityID(Class <? extends Entity > entityClass, String entityName, int id)
    {
        FMLRegistry.registerEntityID(entityClass, entityName, id);
    }

    /**
     * Register a new entity ID
     * 
     * @param entityClass
     * @param entityName
     * @param id
     * @param background
     * @param foreground
     */
    public static void registerEntityID(Class <? extends Entity > entityClass, String entityName, int id, int background, int foreground)
    {
        FMLRegistry.registerEntityID(entityClass, entityName, id, background, foreground);
    }

    public static void registerKey(BaseMod mod, Object keyHandler, boolean allowRepeat)
    {
        // NOOP
    }

    /**
     * Register the mod for packets on this channel. This only registers the
     * channel with Forge Mod Loader, not with clients connecting- use
     * BaseMod.onClientLogin to tell them about your custom channel
     * {@link FMLCommonHandler#registerChannel(cpw.mods.fml.common.ModContainer, String)}
     * 
     * @param mod
     * @param channel
     */
    public static void registerPacketChannel(BaseMod mod, String channel)
    {
        FMLCommonHandler.instance().registerChannel(ModLoaderModContainer.findContainerFor(mod), channel);
    }

    /**
     * Register a new tile entity class
     * 
     * @param tileEntityClass
     * @param id
     */
    public static void registerTileEntity(Class <? extends TileEntity > tileEntityClass, String id)
    {
        FMLRegistry.registerTileEntity(tileEntityClass, id);
    }

    public static void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String id, Object renderer)
    {
        FMLRegistry.instance().registerTileEntity(tileEntityClass, id);
    }

    /**
     * Remove a biome from the list of generated biomes
     *  
     * @param biome
     */
    public static void removeBiome(BiomeBase biome)
    {
        FMLRegistry.removeBiome(biome);
    }

    /**
     * Remove a spawn
     * 
     * @param entityClass
     * @param spawnList
     */
    public static void removeSpawn(Class <? extends EntityLiving > entityClass, EnumCreatureType spawnList)
    {
        FMLRegistry.removeSpawn(entityClass, spawnList, FMLBukkitHandler.instance().getDefaultOverworldBiomes());
    }

    /**
     * Remove a spawn
     * 
     * @param entityClass
     * @param spawnList
     * @param biomes
     */
    public static void removeSpawn(Class <? extends EntityLiving > entityClass, EnumCreatureType spawnList, BiomeBase... biomes)
    {
        FMLRegistry.removeSpawn(entityClass, spawnList, biomes);
    }

    /**
     * Remove a spawn
     * 
     * @param entityName
     * @param spawnList
     */
    public static void removeSpawn(String entityName, EnumCreatureType spawnList)
    {
        FMLRegistry.removeSpawn(entityName, spawnList, FMLBukkitHandler.instance().getDefaultOverworldBiomes());
    }

    /**
     * Remove a spawn
     * 
     * @param entityName
     * @param spawnList
     * @param biomes
     */
    public static void removeSpawn(String entityName, EnumCreatureType spawnList, BiomeBase... biomes)
    {
        FMLRegistry.removeSpawn(entityName, spawnList, biomes);
    }

    @Deprecated
    public static boolean renderBlockIsItemFull3D(int modelID)
    {
        return false;
    }

    @Deprecated
    public static void renderInvBlock(Object renderer, Block block, int metadata, int modelID)
    {
        // NOOP
    }

    @Deprecated
    public static boolean renderWorldBlock(Object renderer, IBlockAccess world, int x, int y, int z, Block block, int modelID)
    {
        return false;
    }

    /**
     * Configuration is handled elsewhere
     * {@link ModLoaderModContainer}
     */
    @Deprecated
    public static void saveConfig()
    {
    }

    /**
     * Send a chat message to the server
     * {@link FMLServerHandler#handleChatPacket(Packet3Chat, EntityPlayer)}
     * 
     * @param text
     */
    @Deprecated
    public static void serverChat(String text)
    {
        //TODO
    }

    @Deprecated
    public static void serverLogin(Object handler, Packet1Login loginPacket)
    {
        //TODO
    }

    /**
     * Indicate that you want to receive ticks
     *
     * @param mod receiving the events
     * @param enable indicates whether you want to recieve them or not
     * @param useClock Not used in server side: all ticks are sent on the server side (no render subticks)
     */
    public static void setInGameHook(BaseMod mod, boolean enable, boolean useClock)
    {
    	ModLoaderHelper.updateStandardTicks(mod, enable, useClock);
    }

    
    public static void setInGUIHook(BaseMod mod, boolean enable, boolean useClock)
    {
        ModLoaderHelper.updateGUITicks(mod, enable, useClock);
    }

    /**
     * Set a private field to a value using reflection
     * {@link ReflectionHelper#setPrivateValue(Class, Object, int, Object)}
     * 
     * @param instanceclass
     * @param instance
     * @param fieldindex
     * @param value
     */
    public static <T, E> void setPrivateValue(Class <? super T > instanceclass, T instance, int fieldindex, E value)
    {
        ReflectionHelper.setPrivateValue(instanceclass, instance, fieldindex, value);
    }

    /**
     * Set a private field to a value using reflection
     * {@link ReflectionHelper#setPrivateValue(Class, Object, String, Object)}
     * 
     * @param instanceclass
     * @param instance
     * @param field
     * @param value
     */
    public static <T, E> void setPrivateValue(Class <? super T > instanceclass, T instance, String field, E value)
    {
        ReflectionHelper.setPrivateValue(instanceclass, instance, field, value);
    }

    /**
     * This method is a call in hook from modified external code. Implemented elsewhere.
     * {@link FMLBukkitHandler#onItemCrafted(EntityHuman, ItemStack, IInventory)}
     * 
     * @param player
     * @param item
     * @param matrix
     */
    @Deprecated
    public static void takenFromCrafting(EntityHuman player, ItemStack item, IInventory matrix)
    {
    }

    /**
     * This method is a call in hook from modified external code. Implemented elsewhere.
     * {@link FMLBukkitHandler#onItemSmelted(EntityHuman, ItemStack)}
     * 
     * @param player
     * @param item
     */
    @Deprecated
    public static void takenFromFurnace(EntityHuman player, ItemStack item)
    {
    }

    /**
     * Throw the offered exception. Likely will stop the game.
     * {@link FMLBukkitHandler#raiseException(Throwable, String, boolean)}
     * @param message
     * @param e
     */
    public static void throwException(String message, Throwable e)
    {
        FMLBukkitHandler.instance().raiseException(e, message, true);
    }

    /**
     * To properly implement packet 250 protocol you should always check your channel
     * is active prior to sending the packet
     *
     * @param player
     * @param channel
     * @return
     */
    public static boolean isChannelActive(EntityHuman player, String channel)
    {
        return FMLCommonHandler.instance().isChannelActive(channel, player);
    }

    /**
     * Added for compatability for some mods
     */
    public static void setupProperties(Class mod) throws IllegalArgumentException, IllegalAccessException, IOException, SecurityException, NoSuchFieldException, NoSuchAlgorithmException, DigestException {
        List<Field> fieldList = new LinkedList<>();
        Properties modprops = new Properties();
        int currentHash = 0;
        int cfgHash = 0;
        Logger logger = getLogger();
        File cfgdir = Loader.instance().getConfigDir();
        File modcfgfile = new File(cfgdir, mod.getSimpleName() + ".cfg");
        if (modcfgfile.exists() && modcfgfile.canRead()) {
            modprops.load(Files.newInputStream(modcfgfile.toPath()));
        }

        if (modprops.containsKey("checksum")) {
            cfgHash = Integer.parseInt(modprops.getProperty("checksum"), 36);
        }

        Field[] var9;
        int var8 = (var9 = mod.getDeclaredFields()).length;

        for(int var7 = 0; var7 < var8; ++var7) {
            Field field = var9[var7];
            if ((field.getModifiers() & 8) != 0 && field.isAnnotationPresent(MLProp.class)) {
                fieldList.add(field);
                Object currentvalue = field.get(null);
                currentHash += currentvalue.hashCode();
            }
        }

        StringBuilder helptext = new StringBuilder();
        Iterator<Field> var21 = fieldList.iterator();

        while(true) {
            String key;
            Object currentvalue;
            Object value;
            double num;
            Field field;
            MLProp annotation;
            do {
                do {
                    while(true) {
                        do {
                            do {
                                if (!var21.hasNext()) {
                                    modprops.put("checksum", Integer.toString(currentHash, 36));
                                    if (!modprops.isEmpty() && (modcfgfile.exists() || modcfgfile.createNewFile()) && modcfgfile.canWrite()) {
                                        modprops.store(new FileOutputStream(modcfgfile), helptext.toString());
                                    }

                                    return;
                                }

                                field = (Field)var21.next();
                            } while((field.getModifiers() & 8) == 0);
                        } while(!field.isAnnotationPresent(MLProp.class));

                        Class<?> type = field.getType();
                        annotation = field.getAnnotation(MLProp.class);
                        key = annotation.name().isEmpty() ? field.getName() : annotation.name();
                        currentvalue = field.get(null);
                        StringBuilder range = new StringBuilder();
                        if (annotation.min() != Double.NEGATIVE_INFINITY) {
                            range.append(String.format(",>=%.1f", annotation.min()));
                        }

                        if (annotation.max() != Double.POSITIVE_INFINITY) {
                            range.append(String.format(",<=%.1f", annotation.max()));
                        }

                        StringBuilder info = new StringBuilder();
                        if (!annotation.info().isEmpty()) {
                            info.append(" -- ");
                            info.append(annotation.info());
                        }

                        helptext.append(String.format("%s (%s:%s%s)%s\n", key, type.getName(), currentvalue, range, info));
                        if (cfgHash == currentHash && modprops.containsKey(key)) {
                            String strvalue = modprops.getProperty(key);
                            value = null;
                            if (type.isAssignableFrom(String.class)) {
                                value = strvalue;
                            } else if (type.isAssignableFrom(Integer.TYPE)) {
                                value = Integer.parseInt(strvalue);
                            } else if (type.isAssignableFrom(Short.TYPE)) {
                                value = Short.parseShort(strvalue);
                            } else if (type.isAssignableFrom(Byte.TYPE)) {
                                value = Byte.parseByte(strvalue);
                            } else if (type.isAssignableFrom(Boolean.TYPE)) {
                                value = Boolean.parseBoolean(strvalue);
                            } else if (type.isAssignableFrom(Float.TYPE)) {
                                value = Float.parseFloat(strvalue);
                            } else if (type.isAssignableFrom(Double.TYPE)) {
                                value = Double.parseDouble(strvalue);
                            }
                            break;
                        }

                        logger.finer(key + " not in config, using default: " + currentvalue);
                        modprops.setProperty(key, currentvalue.toString());
                    }
                } while(value == null);

                if (!(value instanceof Number)) {
                    break;
                }

                num = ((Number)value).doubleValue();
            } while(annotation.min() != Double.NEGATIVE_INFINITY && num < annotation.min() || annotation.max() != Double.POSITIVE_INFINITY && num > annotation.max());

            logger.finer(key + " set to " + value);
            if (!value.equals(currentvalue)) {
                field.set(null, value);
            }
        }
    }
}