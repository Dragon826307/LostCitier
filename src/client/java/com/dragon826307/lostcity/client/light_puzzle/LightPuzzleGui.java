package com.dragon826307.lostcity.client.light_puzzle;

import com.dragon826307.lostcity.client.LostCitierClient;
import com.dragon826307.lostcity.client.util.Render;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class LightPuzzleGui extends Screen {
    private static double d = 0;
    protected static int Size = 3,s = 3;
    private int PosX;
    private int PosY;
    protected static int[][] light = new int[15][15];
    protected static boolean isAble = true;
    public LightPuzzleGui(Component title) {
        super(title);
    }
    private static final Component MESSAGE_0 = Component.translatable("lostcitier.light_puzzle.message_0");
    private static final Component MESSAGE_1 = Component.translatable("lostcitier.light_puzzle.message_1");
    private static final Component MESSAGE_2 = Component.translatable("lostcitier.light_puzzle.message_2");
    private static final Component MESSAGE_3 = Component.translatable("lostcitier.light_puzzle.message_3");
    private static final Component MESSAGE_4 = Component.translatable("lostcitier.light_puzzle.message_4");
    private static final Component MESSAGE_5 = Component.translatable("lostcitier.light_puzzle.message_5");
    @Override
    protected void init() {
        super.init();
        PosX = (this.width/2)-(Size*8)+2;
        PosY = (this.height/2)-(Size*8)+2;
        for (int x = 0; x < Size; x++){
            for (int y = 0; y < Size; y++){
                newButton(x,y);
            }
        }
        AbstractSliderButton PuzzleSize = new AbstractSliderButton(this.width - 104, 2, 60, 20, Component.translatable("lostcitier.light_puzzle.size",s), d) {
            @Override
            protected void updateMessage() {

            }

            @Override
            protected void applyValue() {
                d = this.value;
                s = (int) (this.value*12.99+3);
                setMessage(Component.translatable("lostcitier.light_puzzle.size",s));
            }
        };
        this.addRenderableWidget(PuzzleSize);
        Button SizeConfirmButton = Button.builder(Component.translatable("lostcitier.light_puzzle.size_confirm"),button -> {
            Size = s;
            rebuildWidgets();
        }).pos(this.width-42,2).size(40,20).build();
        this.addRenderableWidget(SizeConfirmButton);
        Button ResetAllButton = Button.builder(Component.translatable("lostcitier.light_puzzle.reset_all"), button -> {
            Size = 3;
            s = 3;
            d = 0;
            for (int x = 0; x < 15; x++){
                for (int y = 0; y < 15; y++){
                    light[x][y] = 0;
                }
            }
            rebuildWidgets();
        }).pos(this.width-104,this.height-22).size(102,20).build();
        this.addRenderableWidget(ResetAllButton);
        Button ExitButton = Button.builder(Component.translatable("lostcitier.light_puzzle.exit"),button -> this.onClose()).pos(this.width-104,this.height-44).size(102,20).build();
        this.addRenderableWidget(ExitButton);
        Button StartSolvingButton = Button.builder(Component.translatable("lostcitier.light_puzzle.start_solving"),button -> {
            int[][] correct_light = new int[Size][Size];
            for (int x = 0; x < Size; x++){
                System.arraycopy(light[x], 0, correct_light[x], 0, Size);
            }
            boolean[] solutionVector = LightPuzzleSolver.lightPuzzleSolver(correct_light);
            if (solutionVector == null) {
                this.minecraft.gui.getChat().addMessage(Component.empty().append(LostCitierClient.ModID).append(Component.translatable("lostcitier.light_puzzle.message_no_solution").withStyle(ChatFormatting.RED, ChatFormatting.BOLD)));
            } else {
                this.minecraft.gui.getChat().addMessage(Component.empty().append(LostCitierClient.ModID).append(Component.translatable("lostcitier.light_puzzle.message_has_solution").withStyle(ChatFormatting.GREEN, ChatFormatting.BOLD)
                ));
                int i = 0;
                for (boolean b : solutionVector) {
                    if (b)
                        this.minecraft.gui.getChat().addMessage(Component.translatable("lostcitier.light_puzzle.message_solution_step", i % Size, i / Size));
                    i++;
                }
            }
        }).pos(2,2).size(80,20).build();
        this.addRenderableWidget(StartSolvingButton);
    }

    //文字渲染
    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int i, int j, float f) {
        super.render(guiGraphics, i, j, f);
        Render.pushPose(guiGraphics);
        Render.translate(guiGraphics,this.width-97,26,1);
        Render.scale(guiGraphics,0.9f,0.9f,1);
        Render.text(guiGraphics,MESSAGE_0, 0, 0,0xFFFFFFFF);
        Render.text(guiGraphics,MESSAGE_3, 0, 30,0xFFFFFFFF);
        Render.text(guiGraphics,MESSAGE_4, 0, 40,0xFFFFFFFF);
        Render.text(guiGraphics,MESSAGE_5, 0, 50,0xFFFFFFFF);
        Render.scale(guiGraphics,0.65f,0.65f,1);
        Render.text(guiGraphics,MESSAGE_1, 0, 20,0xFFFFFFFF);
        Render.text(guiGraphics,MESSAGE_2, 0, 30,0xFFFFFFFF);
        Render.translate(guiGraphics,-this.width*0.8547f+132, (float) (this.height*0.8547f-52-Size*13.8),1);
        Render.text(guiGraphics,Component.translatable("lostcitier.light_puzzle.current_size",Size),0,0,0xFFFFFFFF);
        Render.popPose(guiGraphics);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private void newButton(int x, int y) {
        Button button = Button.builder(Component.literal(switch (light[x][y]){
            case 0 -> "0";
            case 1 -> "1";
            default -> "X";
        }), button1 -> {
            light[x][y]++;
            light[x][y]%=3;
            button1.setMessage(Component.literal(switch (light[x][y]){
                case 0 -> "0";
                case 1 -> "1";
                default -> "X";
            }));
        }).size(14, 14).pos(PosX+x*16, PosY+y*16).build();
        button.active = isAble;
        this.addRenderableWidget(button);
    }
}