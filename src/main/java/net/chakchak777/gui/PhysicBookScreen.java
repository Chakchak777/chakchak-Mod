package net.chakchak777.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.chakchak777.ChakchakMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class PhysicBookScreen extends Screen {

    public static final List<ResourceLocation> PAGES= List.of(
            ResourceLocation.fromNamespaceAndPath(ChakchakMod.MODID, "book/page_1"),
            ResourceLocation.fromNamespaceAndPath(ChakchakMod.MODID, "book/page_2"),
            ResourceLocation.fromNamespaceAndPath(ChakchakMod.MODID, "book/page_3")
    );

    private int currentPage=0;

    private final int imageWidth = 256;
    private final int imageHeight = 256;

    private Button nextButton;
    private Button prevButton;

    public PhysicBookScreen() {
        super(Component.empty());
    }

    @Override
    protected void init() {
        super.init();

        int leftPos=(this.width-this.imageWidth)/2;
        int topPos =(this.height-this.imageHeight)/2;


        WidgetSprites nextSprites = new WidgetSprites(
                ResourceLocation.withDefaultNamespace("widget/page_forward"),
                ResourceLocation.withDefaultNamespace("widget/page_forward_highlighted")
        );


        WidgetSprites prevSprites = new WidgetSprites(
                ResourceLocation.withDefaultNamespace("widget/page_backward"),
                ResourceLocation.withDefaultNamespace("widget/page_backward_highlighted")
        );

        this.nextButton = this.addRenderableWidget(
                new ImageButton(
                        leftPos + 165, topPos + 185, //200 200
                        23 , 13,
                        nextSprites,
                        (button) -> {
                            if (currentPage < PAGES.size() - 1) {
                                currentPage++;
                                updateButtonVisibility();
                                playPageTurnSound();
                            }
                        }
                )
        );


        this.prevButton = this.addRenderableWidget(
                new ImageButton(
                        leftPos + 65, topPos + 185,
                        23, 13,
                        prevSprites,
                        (button) -> {
                            if (currentPage > 0) {
                                currentPage--;
                                updateButtonVisibility();
                                playPageTurnSound();
                            }
                        }
                )
        );


        updateButtonVisibility();
    }

    private void updateButtonVisibility(){
        this.nextButton.visible=(currentPage<PAGES.size()-1);
        this.prevButton.visible=(currentPage>0);
    }
    private void playPageTurnSound() {
        if (this.minecraft != null && this.minecraft.player != null) {
                        this.minecraft.player.playSound(
                    net.minecraft.sounds.SoundEvents.BOOK_PAGE_TURN,
                    1.0F, 1.0F
            );
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

        int leftPos = (this.width - this.imageWidth) / 2;
        int topPos = (this.height - this.imageHeight) / 2;



        ResourceLocation currentTexture = PAGES.get(currentPage);

        RenderSystem.enableBlend();



        guiGraphics.blitSprite(currentTexture, leftPos, topPos, this.imageWidth, this.imageHeight);

        RenderSystem.disableBlend();


        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }


    @Override
    protected void renderBlurredBackground(float partialTick) {
    }


}
