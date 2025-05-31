package com.yut.ui.javaFX;

import com.yut.controller.view_interfaces.GameScreenInterface;
import com.yut.model.Yut;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.*;

public class GameScreenFX extends BorderPane implements GameScreenInterface {

    private List<PieceFX> pieceList = new ArrayList<>();
    private final Map<Integer, Integer> piecesEntered = new HashMap<>();
    private final Map<Integer, PlayerCanvasFX> playerCanvases = new HashMap<>();

    private int boardType;
    private int playerCount;
    private static int defaultPieceCount = 4;

    private Color[] playerColors = {
            Color.LIGHTCORAL,
            Color.LIGHTBLUE,
            Color.YELLOWGREEN,
            Color.PLUM
    };

    private List<ClickableNodeFX> clickableNodes;
    private Map<Integer, ClickableNodeFX> nodeMap = new HashMap<>();

    private BoardCanvasFX boardCanvas;
    private Integer selectedPieceId = null;
    private boolean awaitingMove = false;
    private ClickableNodeFX currentHintNode = null;

    private Circle previewCircle;
    private Rectangle selectRectangle;

    private StackPane layeredBoard = new StackPane();
    private Runnable onBack;

    private ControlPanelFX controlPanel;
    private TextArea deckDisplayArea;

    ImageView firtstYut;
    ImageView secondYut;
    ImageView thirdYut;
    ImageView fourthYut;

    public GameScreenFX(int boardType, int playerCount, Runnable onBack) {

        // Font customFont20 = Font.loadFont(
        // getClass().getResource("/assets/fonts/SF_HailSnow.ttf").toExternalForm(),
        // 20);

        // Font customFont50 = Font.loadFont(
        // getClass().getResource("/assets/fonts/SF_HailSnow.ttf").toExternalForm(),
        // 50);

        this.boardType = boardType;
        this.playerCount = playerCount;
        this.onBack = onBack;

        BackgroundImage backgroundImage = new BackgroundImage(
                new javafx.scene.image.Image(
                        getClass().getResource("/assets/img/background_game.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, true));
        setBackground(new Background(backgroundImage));

        // 시작 버튼 상단
        HBox topPanel = new HBox();
        topPanel.setPadding(new Insets(10));
        topPanel.setAlignment(Pos.CENTER_LEFT);
        topPanel.setPrefHeight(1000);

        Button backButton = new Button("← 시작 화면으로");
        backButton.setPrefWidth(160);
        backButton.setPrefHeight(160);
        backButton.setMaxHeight(Double.MAX_VALUE);

        backButton.setOnAction(e -> onBack.run());
        // backButton.setFont(customFont20);
        topPanel.getChildren().add(backButton);

        // 보드 및 노드
        boardCanvas = new BoardCanvasFX(boardType);
        clickableNodes = boardCanvas.getClickableNodes();
        for (ClickableNodeFX node : clickableNodes) {
            nodeMap.put(node.getNodeID(), node);
        }
        boardCanvas.setPrefHeight(1500);

        layeredBoard.setId("layered-board");
        layeredBoard.getChildren().add(boardCanvas);

        StackPane boardPane = new StackPane(layeredBoard);
        boardPane.setPadding(new Insets(10));

        // 덱 표시창
        deckDisplayArea = new TextArea();
        deckDisplayArea.setEditable(false);
        deckDisplayArea.setWrapText(true);
        deckDisplayArea.setPrefWidth(180);
        deckDisplayArea.setPrefHeight(300);

        // 컨트롤 패널 (사진, 버튼 등)
        controlPanel = new ControlPanelFX();
        controlPanel.getGoalButton().setVisible(true);

        Image image = new Image(getClass().getResource("/assets/img/yut_small_backward.png").toExternalForm());
        Image image2 = new Image(getClass().getResource("/assets/img/yut_small_upward.png").toExternalForm());
        Image image3 = new Image(getClass().getResource("/assets/img/yut_small_backdo.png").toExternalForm());

        firtstYut = new ImageView(image3);
        firtstYut.setFitWidth(30);
        firtstYut.setPreserveRatio(true);

        secondYut = new ImageView(image2);
        secondYut.setFitWidth(30);
        secondYut.setPreserveRatio(true);

        thirdYut = new ImageView(image2);
        thirdYut.setFitWidth(30);
        thirdYut.setPreserveRatio(true);

        fourthYut = new ImageView(image);
        fourthYut.setFitWidth(30);
        fourthYut.setPreserveRatio(true);

        HBox soohyun = new HBox(15, firtstYut, secondYut, thirdYut, fourthYut);

        VBox rightOfBoard = new VBox(15, deckDisplayArea, soohyun);
        rightOfBoard.setAlignment(Pos.CENTER);
        rightOfBoard.setFillWidth(false);

        // 보드 + 덱/컨트롤 패널 수평 정렬
        HBox centerArea = new HBox(20, boardPane, rightOfBoard);
        centerArea.setAlignment(Pos.CENTER);

        // 버튼 영역
        HBox bottomPanel = new HBox(30);
        bottomPanel.setPrefHeight(1000);
        bottomPanel.setAlignment(Pos.CENTER);
        bottomPanel.setPadding(new Insets(20));
        bottomPanel.getChildren().addAll(
                controlPanel.getMoveNewPieceButton(),
                controlPanel.getGoalButton(),
                controlPanel.getRandomButton(),
                controlPanel.getSelectButton());
        controlPanel.getMoveNewPieceButton().setPrefHeight(200);
        controlPanel.getGoalButton().setPrefHeight(200);
        controlPanel.getRandomButton().setPrefHeight(200);
        controlPanel.getSelectButton().setPrefHeight(200);

        // 왼쪽 UI 전체 묶음
        VBox leftUI = new VBox(20, topPanel, centerArea, bottomPanel);
        leftUI.setAlignment(Pos.TOP_CENTER);
        leftUI.setPadding(new Insets(10));

        // 플레이어 표시 영역
        Label statusLabel = new Label("");

        VBox playersBox = new VBox(10);
        playersBox.setAlignment(Pos.CENTER);
        for (int i = 1; i <= playerCount; i++) {
            piecesEntered.put(i, 0);
            Color color = playerColors[(i - 1) % playerColors.length];
            PlayerCanvasFX player = new PlayerCanvasFX(i, defaultPieceCount, color);
            playerCanvases.put(i, player);
            playersBox.getChildren().add(player);
        }

        VBox playerSection = new VBox(5, statusLabel, playersBox);
        playerSection.setAlignment(Pos.TOP_CENTER);
        playerSection.setPadding(new Insets(10));

        // 전체 배치: 좌(전체 UI) + 우(플레이어)
        HBox mainLayout = new HBox(40, leftUI, playerSection);
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.setPadding(new Insets(20));
        setCenter(mainLayout);
    }

    @Override
    public void printDeckContents(Deque<Integer> deck) {
        StringBuilder sb = new StringBuilder();
        if (deck.isEmpty()) {
            sb.append("(덱이 비어 있습니다)");
        } else {
            for (Integer obj : deck) {
                sb.append(Yut.getYutName(obj)).append(" ");
            }
        }
        deckDisplayArea.setText(sb.toString());
    }

    @Override
    public void drawPiece(int nodeID, int playerID, int pieceNumber) {
        ClickableNodeFX node = nodeMap.get(nodeID);
        if (node == null)
            throw new RuntimeException("Node not found");

        Circle piece = new Circle(10, playerColors[playerID - 1]);
        piece.setUserData(pieceNumber);
        pieceList.add(new PieceFX(piece, nodeID));

        StackPane.setMargin(piece, new Insets(node.getNodeY(), 0, 0, node.getNodeX()));
        layeredBoard.getChildren().add(piece);
    }

    @Override
    public void deletePiece(int nodeID) {
        PieceFX toRemove = null;
        for (PieceFX piece : pieceList) {
            if (piece.nodeID == nodeID) {
                layeredBoard.getChildren().remove(piece.circle);
                toRemove = piece;
                break;
            }
        }
        if (toRemove != null)
            pieceList.remove(toRemove);
    }

    @Override
    public int getNodeState(int nodeID) {
        return nodeMap.get(nodeID).getState();
    }

    @Override
    public void updatePlayerCanvas(int playerID, int pieceCount) {
        playerCanvases.get(playerID).setPieceCount(pieceCount);
    }

    @Override
    public void showMovePreview(int nodeID, int playerID) {
        ClickableNodeFX node = nodeMap.get(nodeID);
        previewCircle = new Circle(10, playerColors[playerID - 1]);
        StackPane.setMargin(previewCircle, new Insets(node.getNodeY(), 0, 0, node.getNodeX()));
        layeredBoard.getChildren().add(previewCircle);
    }

    @Override
    public void deleteMovePreview() {
        if (previewCircle != null) {
            layeredBoard.getChildren().remove(previewCircle);
            previewCircle = null;
        }
    }

    @Override
    public void select(int nodeID) {
        ClickableNodeFX node = nodeMap.get(nodeID);
        selectRectangle = new Rectangle(20, 20);
        selectRectangle.setStroke(Color.BLACK);
        selectRectangle.setFill(Color.TRANSPARENT);
        StackPane.setMargin(selectRectangle, new Insets(node.getNodeY(), 0, 0, node.getNodeX()));
        layeredBoard.getChildren().add(selectRectangle);
    }

    @Override
    public void highlightCurrentPlayer(int currentPlayerId) {
        for (int i = 1; i <= playerCount; i++) {
            playerCanvases.get(i).setHighlighted(i == currentPlayerId);
        }
    }

    @Override
    public void updateRandomResult(int yut) {
    }

    // @Override
    // public void updateRandomResult(int[] yut) {
    // Image image = new
    // Image(getClass().getResource("/assets/img/yut_small_backward.png").toExternalForm());
    // Image image2 = new
    // Image(getClass().getResource("/assets/img/yut_small_upward.png").toExternalForm());
    // Image image3 = new
    // Image(getClass().getResource("/assets/img/yut_small_backdo.png").toExternalForm());

    // if (yut[0] == 0) {
    // this.firtstYut.setImage(image3);
    // } else {
    // this.firtstYut.setImage(image2);
    // }

    // if (yut[1] == 0) {
    // this.secondYut.setImage(image);
    // } else {
    // this.secondYut.setImage(image2);
    // }

    // if (yut[2] == 0) {
    // this.thirdYut.setImage(image);
    // } else {
    // this.thirdYut.setImage(image2);
    // }

    // if (yut[3] == 0) {
    // this.fourthYut.setImage(image);
    // } else {
    // this.fourthYut.setImage(image2);
    // }
    // }

    @Override
    public void addRandomThrowButtonListener(EventHandler<ActionEvent> listener) {
        controlPanel.getRandomButton().setOnAction(listener);
    }

    @Override
    public void addSelectedThrowButtonListener(int index, EventHandler<ActionEvent> listener) {
        if (index >= 0 && index < controlPanel.getYutButtons().length) {
            controlPanel.getYutButtons()[index].setOnAction(listener);
        }
    }

    @Override
    public void addBackButtonListener(EventHandler<ActionEvent> listener) {

    }

    @Override
    public void addNodeClickListener(ClickableNodeFX node, EventHandler<MouseEvent> listener) {
        node.setOnMouseClicked(listener);
    }

    @Override
    public void addMoveNewPieceButtonListener(EventHandler<ActionEvent> listener) {
        controlPanel.getMoveNewPieceButton().setOnAction(listener);
    }

    @Override
    public void addGoalButtonListener(EventHandler<ActionEvent> listener) {
        controlPanel.getGoalButton().setOnAction(listener);
    }

    @Override
    public void setGoalButtonVisible(boolean visible) {
        controlPanel.getGoalButton().setVisible(visible);
    }

    @Override
    public Map<Integer, ClickableNodeFX> getNodeMap() {
        return nodeMap;
    }

    private static class PieceFX {
        Circle circle;
        int nodeID;

        PieceFX(Circle circle, int nodeID) {
            this.circle = circle;
            this.nodeID = nodeID;
        }
    }
}
