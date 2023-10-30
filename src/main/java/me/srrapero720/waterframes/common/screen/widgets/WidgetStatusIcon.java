package me.srrapero720.waterframes.common.screen.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import me.srrapero720.waterframes.common.screen.widgets.styles.WidgetIcons;
import me.srrapero720.watermedia.api.image.ImageCache;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import team.creative.creativecore.common.gui.GuiChildControl;
import team.creative.creativecore.common.gui.style.GuiIcon;
import team.creative.creativecore.common.util.math.geo.Rect;
import team.creative.creativecore.common.util.text.TextBuilder;

import java.util.function.Supplier;

public class WidgetStatusIcon extends WidgetIcon {
    private final Supplier<ImageCache> cacheSupplier;
    public WidgetStatusIcon(String name, int width, int height, GuiIcon icon, Supplier<ImageCache> cacheSupplier) {
        super(name, width, height, icon);
        this.cacheSupplier = cacheSupplier;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void renderContent(PoseStack pose, GuiChildControl guiChildControl, Rect rect, int i, int i1) {
        ImageCache cache = cacheSupplier.get();
        icon = switch (cache != null ? cache.getStatus() : ImageCache.Status.FAILED) {
            case READY -> {
                setTooltip(new TextBuilder().add(new TranslatableComponent("label.waterframes.operative")).build());
                yield  WidgetIcons.STATUS_OK;
            }
            case LOADING -> {
                setTooltip(new TextBuilder().add(new TranslatableComponent("label.waterframes.loading")).build());
                yield WidgetIcons.STATUS_ALERT;
            }
            case WAITING, FORGOTTEN -> {
                if (cacheSupplier.get().url.isEmpty()) {
                    setTooltip(new TextBuilder().add(new TranslatableComponent("label.waterframes.idle")).build());
                    yield WidgetIcons.STATUS_IDLE;
                } else {
                    setTooltip(new TextBuilder().add(new TranslatableComponent("label.waterframes.wrong")).build());
                    yield WidgetIcons.STATUS_PEM;
                }
            }
            case FAILED -> {
                if (cache != null) {
                    setTooltip(new TextBuilder(cacheSupplier.get().getException().getMessage()).build());
                } else {
                    setTooltip(new TextBuilder().add(new TranslatableComponent("download.exception.invalid")).build());
                }
                yield WidgetIcons.STATUS_ERROR;
            }
        };
        super.renderContent(pose, guiChildControl, rect, i, i1);
    }
}