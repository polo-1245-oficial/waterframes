package me.srrapero720.waterframes.common.block.data;

import me.srrapero720.waterframes.DisplayConfig;
import me.srrapero720.waterframes.common.block.entity.DisplayTile;
import me.srrapero720.waterframes.common.screens.widgets.WidgetCheckButton;
import me.srrapero720.waterframes.util.FrameTools;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import team.creative.creativecore.common.gui.GuiLayer;
import team.creative.creativecore.common.gui.controls.simple.*;
import team.creative.creativecore.common.util.math.vec.Vec2f;

public class DisplayData {
    public static final String URL = "url";
    public static final String ACTIVE = "active";
    public static final String MIN_X = "min_x";
    public static final String MIN_Y = "min_y";
    public static final String MAX_X = "max_x";
    public static final String MAX_Y = "max_y";

    public static final String FLIP_X = "flip_x";
    public static final String FLIP_Y = "flip_y";

    public static final String ROTATION = "rotation";
    public static final String ALPHA = "alpha";
    public static final String BRIGHTNESS = "brightness";
    public static final String RENDER_DISTANCE = "render_distance";

    public static final String VOLUME = "volume";
    public static final String VOL_RANGE_MIN= "volume_min_range";
    public static final String VOL_RANGE_MAX = "volume_max_range";
    public static final String LOOP = "loop";
    public static final String PLAYING = "playing";
    public static final String TICK = "tick";
    public static final String TICK_MAX = "tick_max";
    public static final String DATA_V = "data_v";

    // FRAME KEYS
    public static final String VISIBLE_FRAME = "frame_visibility";
    public static final String RENDER_BOTH_SIDES = "render_both";

    // PROJECTOR
    public static final String PROJECTION_DISTANCE = "projection_distance";
    public static final String AUDIO_OFFSET = "audio_offset";

    public static final short V = 1;

    public String url = "";
    public boolean active = true;
    public final Vec2f min = new Vec2f(0f, 0f);
    public final Vec2f max = new Vec2f(1f, 1f);

    public boolean flipX = false;
    public boolean flipY = false;

    public float rotation = 0;
    public float alpha = 1;
    public float brightness = 1;
    public short renderDistance = DisplayConfig.maxRenderDistance((short) 32);

    public int volume = DisplayConfig.maxVolume();
    public int maxVolumeDistance = Math.min(20, DisplayConfig.maxVolumeDistance());
    public int minVolumeDistance = Math.min(5, maxVolumeDistance);

    public boolean loop = true;
    public boolean playing = true;
    public int tick = 0;
    public int tickMax = -1;

    // FRAME VALUES
    public boolean frameVisibility = true;
    public boolean renderBothSides = false;

    // PROJECTOR VALUES
    public short projectionDistance = DisplayConfig.maxProjectionDistance((short) 8);
    public float audioOffset = 0;

    public HorizontalPosition getPosX() { return this.min.x == 0 ? HorizontalPosition.LEFT : this.max.x == 1 ? HorizontalPosition.RIGHT : HorizontalPosition.CENTER; }
    public VerticalPosition getPosY() { return this.min.y == 0 ? VerticalPosition.TOP : this.max.y == 1 ? VerticalPosition.BOTTOM : VerticalPosition.CENTER; }
    public float getWidth() { return this.max.x - this.min.x; }
    public float getHeight() { return this.max.y - this.min.y; }

    public void save(CompoundTag nbt, DisplayTile displayTile) {
        nbt.putString(URL, url);
        nbt.putBoolean(ACTIVE, active);
        if (displayTile.canResize()) {
            nbt.putFloat(MIN_X, min.x);
            nbt.putFloat(MIN_Y, min.y);
            nbt.putFloat(MAX_X, max.x);
            nbt.putFloat(MAX_Y, max.y);
        }
        nbt.putFloat(ROTATION, rotation);
        nbt.putShort(RENDER_DISTANCE, renderDistance);
        nbt.putBoolean(FLIP_X, flipX);
        nbt.putBoolean(FLIP_Y, flipY);
        nbt.putFloat(ALPHA, alpha);
        nbt.putFloat(BRIGHTNESS, brightness);
        nbt.putInt(VOLUME, volume);
        nbt.putInt(VOL_RANGE_MIN, minVolumeDistance);
        nbt.putInt(VOL_RANGE_MAX, maxVolumeDistance);
        nbt.putBoolean(PLAYING, playing);
        nbt.putInt(TICK, tick);
        nbt.putInt(TICK_MAX, tickMax);
        nbt.putBoolean(LOOP, loop);

        if (displayTile.canRenderBackside()) {
            nbt.putBoolean(RENDER_BOTH_SIDES, renderBothSides);
        }

        if (displayTile.canHideModel()) {
            nbt.putBoolean(VISIBLE_FRAME, frameVisibility);
        }

        if (displayTile.canProject()) {
            nbt.putInt(PROJECTION_DISTANCE, projectionDistance);
            nbt.putFloat(AUDIO_OFFSET, audioOffset);
        }

        nbt.putInt(DATA_V, V);
    }

    public void load(CompoundTag nbt, DisplayTile displayTile) {
        this.url = nbt.getString(URL);
        this.active = nbt.contains(ACTIVE) ? nbt.getBoolean(ACTIVE) : this.active;
        if (displayTile.canResize()) {
            this.min.x = nbt.getFloat(MIN_X);
            this.min.y = nbt.getFloat(MIN_Y);
            this.max.x = nbt.getFloat(MAX_X);
            this.max.y = nbt.getFloat(MAX_Y);
        }
        this.rotation = nbt.getFloat(ROTATION);
        this.renderDistance = DisplayConfig.maxRenderDistance(nbt.getShort(RENDER_DISTANCE));
        this.flipX = nbt.getBoolean(FLIP_X);
        this.flipY = nbt.getBoolean(FLIP_Y);
        this.alpha = nbt.contains(ALPHA) ? nbt.getFloat(ALPHA) : alpha;
        this.brightness = nbt.contains(BRIGHTNESS) ? nbt.getFloat(BRIGHTNESS) : alpha;
        this.volume = nbt.contains(VOLUME) ? DisplayConfig.maxVolume(nbt.getInt(VOLUME)) : volume;
        this.maxVolumeDistance = nbt.contains(VOL_RANGE_MAX) ? DisplayConfig.maxVolumeDistance(nbt.getInt(VOL_RANGE_MAX)) : maxVolumeDistance;
        this.minVolumeDistance = nbt.contains(VOL_RANGE_MIN) ? Math.min(nbt.getInt(VOL_RANGE_MIN), maxVolumeDistance) : minVolumeDistance;
        this.playing = nbt.getBoolean(PLAYING);
        this.tick = nbt.getInt(TICK);
        this.tickMax = nbt.getInt(TICK_MAX);
        this.loop = nbt.getBoolean(LOOP);

        if (displayTile.canHideModel()) {
            this.frameVisibility = nbt.getBoolean(VISIBLE_FRAME);
        }

        if (displayTile.canRenderBackside()) {
            this.renderBothSides = nbt.getBoolean(RENDER_BOTH_SIDES);
        }

        if (displayTile.canProject()) {
            projectionDistance = nbt.contains(PROJECTION_DISTANCE) ? DisplayConfig.maxProjectionDistance(nbt.getShort(PROJECTION_DISTANCE)) : projectionDistance;
            audioOffset = nbt.contains(AUDIO_OFFSET) ? nbt.getFloat(AUDIO_OFFSET) : audioOffset;
        }

        switch (nbt.getShort(DATA_V)) {
            case 1 -> {

            }

            default -> { // NO EXISTS
                if (!nbt.contains("maxx")) return; // no exists then ignore, prevents broke new data on 2.0
                this.min.x = nbt.getFloat("minx");
                this.min.y = nbt.getFloat("miny");
                this.max.x = nbt.getFloat("maxx");
                this.max.y = nbt.getFloat("maxy");

                this.flipX = nbt.getBoolean("flipX");
                this.flipY = nbt.getBoolean("flipY");

                this.maxVolumeDistance = DisplayConfig.maxVolumeDistance((int) nbt.getFloat("max"));
                this.minVolumeDistance = Math.min((int) nbt.getFloat("min"), maxVolumeDistance);

                this.renderDistance = nbt.getShort("render");

                if (displayTile.canHideModel()) {
                    this.frameVisibility = nbt.getBoolean("visibleFrame");
                }

                if (displayTile.canRenderBackside()) {
                    this.renderBothSides = nbt.getBoolean("bothSides");
                }
            }
        }

        this.restrictWidth();
        this.restrictHeight();
    }

    public static void setWidth(final DisplayTile block, final HorizontalPosition position, final float width) {
        switch (position) {
            case LEFT -> {
                block.data.min.x = 0;
                block.data.max.x = width;
            }
            case RIGHT -> {
                block.data.min.x = 1 - width;
                block.data.max.x = 1;
            }
            default -> {
                float middle = width / 2;
                block.data.min.x = 0.5F - middle;
                block.data.max.x = 0.5F + middle;
            }
        }
    }

    public static void setHeight(final DisplayTile block, final VerticalPosition position, final float height) {
        switch (position) {
            case TOP -> {
                block.data.min.y = 0;
                block.data.max.y = height;
            }
            case BOTTOM -> {
                block.data.min.y = 1 - height;
                block.data.max.y = 1;
            }
            default -> {
                float middle = height / 2;
                block.data.min.y = 0.5F - middle;
                block.data.max.y = 0.5F + middle;
            }
        }
    }

    private void restrictWidth() {
        float maxWidth = DisplayConfig.maxWidth();
        if (getWidth() > maxWidth) {
            switch (getPosX()) {
                case LEFT -> {
                    this.min.x = 0;
                    this.max.x = maxWidth;
                }
                case RIGHT -> {
                    this.min.x = 1 - maxWidth;
                    this.max.x = 1;
                }
                default -> {
                    float middle = maxWidth / 2f;
                    this.min.x = 0.5F - middle;
                    this.max.x = 0.5F + middle;
                }
            }
        }
    }

    private void restrictHeight() {
        float height = DisplayConfig.maxHeight();
        if (getHeight() > height) {
            switch (getPosY()) {
                case TOP  -> {
                    this.min.y = 0;
                    this.max.y = height;
                }
                case BOTTOM -> {
                    this.min.y = 1 - height;
                    this.max.y = 1;
                }
                default -> {
                    float middle = height / 2;
                    this.min.y = 0.5F - middle;
                    this.max.y = 0.5F + middle;
                }
            }
        }
    }

    public int getOffsetMode() {
        return (audioOffset == projectionDistance) ? 2 : (audioOffset == projectionDistance / 2f) ? 1 : 0;
    }

    public static <BL extends DisplayTile> CompoundTag build(GuiLayer gui, BL displayTile) {
        CompoundTag nbt = new CompoundTag();

        GuiTextfield url = gui.get(URL);
        nbt.putString(URL, url.getText());
        nbt.putBoolean(ACTIVE, true);

        if (displayTile.canResize()) {
            GuiCounterDecimal width = gui.get("width");
            GuiCounterDecimal height = gui.get("height");
            GuiStateButton buttonPosX = gui.get("pos_x");
            GuiStateButton buttonPosY = gui.get("pos_y");
            nbt.putFloat("width", Math.max(0.1F, (float) width.getValue()));
            nbt.putFloat("height", Math.max(0.1F, (float) height.getValue()));
            nbt.putInt("pos_x",  buttonPosX.getState());
            nbt.putInt("pos_y", buttonPosY.getState());
        }

        GuiCheckBox flipX = gui.get(FLIP_X);
        GuiCheckBox flipY = gui.get(FLIP_Y);
        nbt.putBoolean(FLIP_X, flipX.value);
        nbt.putBoolean(FLIP_Y, flipY.value);

        GuiSlider rotation = gui.get(ROTATION);
        nbt.putFloat(ROTATION, (float) rotation.value);

        GuiSlider alpha = gui.get(ALPHA);
        nbt.putFloat(ALPHA, (float) alpha.value);

        GuiSlider brightness = gui.get(BRIGHTNESS);
        nbt.putFloat(BRIGHTNESS, (float) brightness.value);

        GuiSteppedSlider renderDistance = gui.get(RENDER_DISTANCE);
        nbt.putShort(RENDER_DISTANCE, (short) renderDistance.value);

        WidgetCheckButton loop = gui.get(LOOP);
        nbt.putBoolean(LOOP, loop.value);

        GuiSlider volume = gui.get(VOLUME);
        nbt.putByte(VOLUME, (byte) volume.value);

        GuiSteppedSlider min = gui.get(VOL_RANGE_MIN);
        GuiSteppedSlider max = gui.get(VOL_RANGE_MAX);
        nbt.putShort(VOL_RANGE_MIN, (short) min.getValue());
        nbt.putShort(VOL_RANGE_MAX, (short) max.getValue());

        if (displayTile.canHideModel()) {
            GuiCheckBox frameVisibility = gui.get(VISIBLE_FRAME);
            nbt.putBoolean(VISIBLE_FRAME, frameVisibility.value);
        }

        if (displayTile.canRenderBackside()) {
            GuiCheckBox renderBoth = gui.get(RENDER_BOTH_SIDES);
            nbt.putBoolean(RENDER_BOTH_SIDES, renderBoth.value);
        }

        if (displayTile.canProject()) {
            GuiSteppedSlider projection_distance = gui.get(PROJECTION_DISTANCE);
            nbt.putShort(PROJECTION_DISTANCE, (short) projection_distance.value);

            GuiStateButton audioCenter = gui.get(AUDIO_OFFSET);
            nbt.putInt("audio_offset_mode", audioCenter.getState());
        }

        return nbt;
    }

    public static <T extends DisplayTile> void sync(T block, Player player, CompoundTag nbt, ExtraData extra) {
        String url = nbt.getString(URL);
        if (DisplayConfig.canSave(player, url)) {
            if (!block.getUrl().equals(url)) {
                block.data.tick = 0;
                block.data.tickMax = -1;
            }
            block.setUrl(url);
            block.data.active = nbt.getBoolean(ACTIVE);

            if (block.canResize()) {
                float width = FrameTools.minFloat(nbt.getFloat("width"), DisplayConfig.maxWidth());
                float height = FrameTools.minFloat(nbt.getFloat("height"), DisplayConfig.maxHeight());
                int posX = nbt.getInt("pos_x");
                int posY = nbt.getInt("pos_y");

                DisplayData.setWidth(block, HorizontalPosition.VALUES[posX], width);
                DisplayData.setHeight(block, VerticalPosition.VALUES[posY], height);
            }

            block.data.flipX = nbt.getBoolean(FLIP_X);
            block.data.flipY = nbt.getBoolean(FLIP_Y);
            block.data.rotation = nbt.getFloat(ROTATION);
            block.data.alpha = nbt.getFloat(ALPHA);
            block.data.brightness = nbt.getFloat(BRIGHTNESS);
            block.data.renderDistance = DisplayConfig.maxRenderDistance(nbt.getShort(RENDER_DISTANCE));
            block.data.loop = nbt.getBoolean(LOOP);
            block.data.volume = DisplayConfig.maxVolume(nbt.getInt(VOLUME));
            block.data.maxVolumeDistance = DisplayConfig.maxVolumeDistance(nbt.getShort(VOL_RANGE_MAX));
            block.data.minVolumeDistance = Math.min(nbt.getShort(VOL_RANGE_MIN), block.data.maxVolumeDistance);
            if (block.data.minVolumeDistance > block.data.maxVolumeDistance) block.data.maxVolumeDistance = block.data.minVolumeDistance;

            if (block.canHideModel()) {
                block.data.frameVisibility = nbt.getBoolean(VISIBLE_FRAME);
            }

            if (block.canRenderBackside()) {
                block.data.renderBothSides = nbt.getBoolean(RENDER_BOTH_SIDES);
            }

            if (block.canProject()) {
                block.data.projectionDistance = DisplayConfig.maxProjectionDistance(nbt.getShort(PROJECTION_DISTANCE));

                int mode = nbt.getInt("audio_offset_mode");
                block.data.audioOffset = mode == 2 ? block.data.projectionDistance : mode == 1 ? block.data.projectionDistance / 2f : 0;
            }

            if (extra != null) extra.set(block.data);
        }

        block.setDirty();
    }

    public enum VerticalPosition {
        TOP, BOTTOM, CENTER;
        public static final VerticalPosition[] VALUES = values();
    }

    public enum HorizontalPosition {
        LEFT, RIGHT, CENTER;
        public static final HorizontalPosition[] VALUES = values();
    }

    public enum AudioSource {
        BLOCK, BETWEEN, PROJECTION;
        public static final AudioSource[] VALUES = values();
    }

    public interface ExtraData {
        void set(DisplayData data);
    }
}