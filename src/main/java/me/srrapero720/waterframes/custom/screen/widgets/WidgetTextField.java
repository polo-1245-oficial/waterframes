package me.srrapero720.waterframes.custom.screen.widgets;

import me.srrapero720.waterframes.core.tools.UrlTool;
import me.srrapero720.waterframes.core.WaterConfig;
import me.srrapero720.waterframes.custom.screen.widgets.custom.CustomStyles;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import org.jetbrains.annotations.Nullable;
import team.creative.creativecore.common.gui.controls.simple.GuiButton;
import team.creative.creativecore.common.gui.controls.simple.GuiTextfield;
import team.creative.creativecore.common.gui.style.GuiStyle;
import team.creative.creativecore.common.gui.style.display.StyleDisplay;

import java.util.ArrayList;
import java.util.List;

public class WidgetTextField extends GuiTextfield {
    private final GuiButton saveButton;
    private String suggestion_c = "";

    public WidgetTextField(GuiButton saveButton, String name, String text) {
        super(name);
        setMaxStringLength(2048);
        setText(text);
        this.saveButton = saveButton;
    }

    @Override
    public StyleDisplay getBorder(GuiStyle style, StyleDisplay display) {
        return CustomStyles.NORMAL_BORDER;
    }

    @Override
    public StyleDisplay getBackground(GuiStyle style, StyleDisplay display) {
        return CustomStyles.NORMAL_BACKGROUND;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        boolean pressed = super.keyPressed(keyCode, scanCode, modifiers);
        saveButton.setEnabled(WaterConfig.canUse(getPlayer(), getText()));
        return pressed;
    }

    public WidgetTextField setSuggest(String suggest) {
        setSuggestion(suggest);
        return this;
    }

    @Override
    public void setSuggestion(@Nullable String p_195612_1_) {
        if (getText().isEmpty()) super.setSuggestion(p_195612_1_);
        suggestion_c = p_195612_1_;
    }

    @Override
    public void focus() {
        super.setSuggestion("");
        super.focus();
    }

    @Override
    public void looseFocus() {
        if (getText().isEmpty()) super.setSuggestion(suggestion_c);
        super.looseFocus();
    }

    @Override
    public List<Component> getTooltip() {
        var tooltips = new ArrayList<Component>();

        if (!WaterConfig.domainAllowed(getText()))
            tooltips.add(new TranslatableComponent("label.waterframes.not_whitelisted").withStyle(ChatFormatting.RED));
        if (!UrlTool.url_isValid(getText()))
            tooltips.add(new TranslatableComponent("label.waterframes.invalid_url").withStyle(ChatFormatting.RED));

        return tooltips.isEmpty() ? null : tooltips;
    }
}