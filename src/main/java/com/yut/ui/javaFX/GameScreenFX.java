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
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.*;

public class GameScreenFX extends BorderPane implements GameScreenInterface {

    private List<PieceFX> pieceList = new ArrayList<>();
    private final Map<Integer, Integer> piecesEntered = new HashMap<>();
    private final Map<Integer, PlayerCanvasFX> playerCanvases = new HashMap<>();

    private int boardType;
    private int playerCount;
    private int pieceCount = 4;

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

    private Pane layeredBoard = new Pane();
    private Runnable onBack;

    private ControlPanelFX controlPanel;
    private TextArea deckDisplayArea;

    ImageView firtstYut;
    ImageView secondYut;
    ImageView thirdYut;
    ImageView fourthYut;

    Button backButton;

    public GameScreenFX(Scene startScene, int boardType, int playerCount, int pieceCount) {

        // Font customFont20 = Font.loadFont(
        // getClass().getResource("/assets/fonts/SF_HailSnow.ttf").toExternalForm(),
        // 20);

        // Font customFont50 = Font.loadFont(
        // getClass().getResource("/assets/fonts/SF_HailSnow.ttf").toExternalForm(),
        // 50);

        this.boardType = boardType;
        this.playerCount = playerCount;
        this.pieceCount = pieceCount;
        // this.onBack = onBack;

        Font customFont18 = Font.loadFont(
                getClass().getResource("/assets/fonts/SF_HailSnow.ttf").toExternalForm(),
                18);

        BackgroundImage backgroundImage = new BackgroundImage(
                new javafx.scene.image.Image(
                        getClass().getResource("/assets/img/background_game.png").toExternalForm()),

                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(950, 760, false, false, false, false));

        BackgroundImage backgroundImage2 = new BackgroundImage(
                new javafx.scene.image.Image(
                        getClass().getResource("/assets/img/background_game2.png").toExternalForm()),

                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(950, 760, false, false, false, false));

        BackgroundImage backgroundImage3 = new BackgroundImage(
                new javafx.scene.image.Image(
                        getClass().getResource("/assets/img/background_game3.png").toExternalForm()),

                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(950, 760, false, false, false, false));

        if (playerCount == 2) {
            setBackground(new Background(backgroundImage3));
        } else if (playerCount == 3) {
            setBackground(new Background(backgroundImage2));
        } else {
            setBackground(new Background(backgroundImage));
        }

        // 시작 버튼 상단
        HBox topPanel = new HBox();
        topPanel.setPadding(new Insets(10));
        topPanel.setAlignment(Pos.CENTER_LEFT);
        topPanel.setPrefHeight(1000);
        topPanel.setPadding(new Insets(0, 0, 20, 0));

        backButton = new Button("◀  시작 화면으로");
        backButton.setFont(customFont18);
        backButton.setPrefWidth(140);
        backButton.setPrefHeight(80);
        backButton.setMaxHeight(Double.MAX_VALUE);
        // backButton.setPadding(new Insets(10, 0, 10, 0));
        backButton.setOnAction(e -> {
            Stage currentStage = (Stage) this.getScene().getWindow();
            currentStage.setScene(startScene);
        });

        // backButton.setOnAction(e -> onBack.run());
        // backButton.setFont(customFont20);
        topPanel.getChildren().add(backButton);

        // 보드 및 노드
        boardCanvas = new BoardCanvasFX(boardType, 500);
        clickableNodes = boardCanvas.getClickableNodes();
        for (ClickableNodeFX node : clickableNodes) {
            nodeMap.put(node.getNodeID(), node);
        }
        boardCanvas.setPrefHeight(1500);
        boardCanvas.setPrefWidth(510);

        layeredBoard.setId("layered-board");
        layeredBoard.getChildren().add(boardCanvas);

        StackPane boardPane = new StackPane(layeredBoard);
        boardPane.setPadding(new Insets(0, 30, 0, 0));

        // 덱 표시창
        deckDisplayArea = new TextArea();
        deckDisplayArea.setEditable(false);
        deckDisplayArea.setWrapText(true);
        deckDisplayArea.setPrefWidth(180);
        deckDisplayArea.setPrefHeight(340);
        deckDisplayArea.setStyle("""
                    -fx-background-insets: 0;
                    -fx-effect: none;
                """);

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
        VBox.setMargin(deckDisplayArea, new Insets(0, 0, 20, 0));
        VBox.setMargin(soohyun, new Insets(0, 0, 30, 0));
        rightOfBoard.setAlignment(Pos.CENTER);
        rightOfBoard.setFillWidth(false);

        // 테스트 코드
        // Deque<Integer> testDeque = new ArrayDeque<>();
        // testDeque.add(5);
        // testDeque.add(3);
        // testDeque.add(1);
        // printDeckContents(testDeque);

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
        controlPanel.getMoveNewPieceButton().setPadding(new Insets(10, 10, 10, 10));
        controlPanel.getGoalButton().setPadding(new Insets(10, 10, 10, 10));
        controlPanel.getRandomButton().setPadding(new Insets(10, 10, 10, 10));
        controlPanel.getSelectButton().setPadding(new Insets(10, 10, 10, 10));

        // 왼쪽 UI 전체 묶음
        VBox leftUI = new VBox(20, topPanel, centerArea, bottomPanel);
        leftUI.setAlignment(Pos.TOP_CENTER);
        leftUI.setPadding(new Insets(10));
        VBox.setVgrow(centerArea, Priority.ALWAYS);

        // 플레이어 표시 영역
        Label statusLabel = new Label("");

        VBox playersBox = new VBox(10);
        playersBox.setAlignment(Pos.CENTER);
        playersBox.setPrefHeight(760);
        StackPane playersStack = new StackPane();
        playersStack.setPrefSize(180, 720);

        for (int i = 1; i <= playerCount; i++) {
            piecesEntered.put(i, 0);
            Color color = playerColors[(i - 1) % playerColors.length];
            PlayerCanvasFX player = new PlayerCanvasFX(i, pieceCount, color);
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
    public void printDeckContents(Deque<int[]> deck) {
        Font customFont25 = Font.loadFont(
                getClass().getResource("/assets/fonts/SF_HailSnow.ttf").toExternalForm(),
                25);
        deckDisplayArea.setFont(customFont25);

        StringBuilder sb = new StringBuilder();
        for (int[] obj : deck) {
            sb.append(Yut.getYutName(obj[0])).append("\n");
        }
        deckDisplayArea.setText(sb.toString());
    }

    @Override
    public void drawPiece(int nodeID, int playerID, int pieceNumber) {
        ClickableNodeFX node = nodeMap.get(nodeID);
        if (node == null)
            throw new RuntimeException("Node not found");

        // Adjust for radius since PieceFX draws at top-left
        int x = node.getNodeX() - 10;
        int y = node.getNodeY() - 10;

        PieceFX piece = new PieceFX(x, y, playerColors[playerID - 1], pieceNumber);
        pieceList.add(piece);

        node.setOnNodePiece(piece);
        layeredBoard.getChildren().add(piece);
    }

    @Override
    public void deletePiece(int nodeID) {
        ClickableNodeFX node = nodeMap.get(nodeID);
        if (node == null)
            throw new RuntimeException("Node " + nodeID + " not found");

        PieceFX piece = node.getOnNodePiece();
        if (piece == null)
            return;

        node.setOnNodePiece(null);
        layeredBoard.getChildren().remove(piece);
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
        previewCircle = new Circle(20);
        previewCircle.setFill(Color.TRANSPARENT);
        previewCircle.setStroke(playerColors[playerID - 1]);
        previewCircle.setStrokeWidth(5);
        previewCircle.setOpacity(0.5); // optional: make it semi-transparent for preview
        previewCircle.setLayoutX(node.getNodeX());
        previewCircle.setLayoutY(node.getNodeY());
        previewCircle.setMouseTransparent(true);
        layeredBoard.getChildren().add(previewCircle);
        currentHintNode = node;
        node.setPreviewPiece(true);
    }

    @Override
    public void deleteMovePreview() {
        if (previewCircle != null) {
            if (currentHintNode != null) {

                currentHintNode.setPreviewPiece(false);
                currentHintNode = null;
            }
            layeredBoard.getChildren().remove(previewCircle);
            previewCircle = null;
        }
    }

    @Override
    public void select(int nodeID) {
        if (selectRectangle != null) {
            layeredBoard.getChildren().remove(selectRectangle);
        }
        ClickableNodeFX node = nodeMap.get(nodeID);
        if (node == null) {
            throw new RuntimeException("Node " + nodeID + " does not exist");
        }

        selectRectangle = new Rectangle(20, 20);
        selectRectangle.setStroke(Color.BLACK);
        selectRectangle.setFill(Color.TRANSPARENT);
        selectRectangle.setLayoutX(node.getNodeX() - 10); // 10 = half of width (20/2)
        selectRectangle.setLayoutY(node.getNodeY() - 10); // 10 = half of height (20/2)

        node.setPreviewPiece(true);
        layeredBoard.getChildren().add(selectRectangle);
    }

    @Override
    public void highlightCurrentPlayer(int currentPlayerId) {
        for (int i = 1; i <= playerCount; i++) {
            playerCanvases.get(i).setHighlighted(i == currentPlayerId);
        }
    }

    // @Override
    // public void updateRandomResult(int yut) {
    // controlPanel.highlightYutButton(yut);

    // if (yut == 4 || yut == 5) {
    // controlPanel.getMoveNewPieceButton().setStyle("-fx-background-color:
    // lightgray;");
    // controlPanel.getMoveNewPieceButton().setDisable(true);
    // } else {
    // controlPanel.getMoveNewPieceButton().setStyle(""); // Reset to default style
    // controlPanel.getMoveNewPieceButton().setDisable(false);
    // }
    // }

    @Override
    public void updateRandomResult(int[] yut) {

        controlPanel.highlightYutButton(yut[0]);

        if (yut[0] == 4 || yut[0] == 5) {
            controlPanel.getMoveNewPieceButton().setStyle("-fx-background-color: lightgray;");
            controlPanel.getMoveNewPieceButton().setDisable(true);
        } else {
            controlPanel.getMoveNewPieceButton().setStyle(""); // Reset to default style
            controlPanel.getMoveNewPieceButton().setDisable(false);
        }

        Image image = new Image(getClass().getResource("/assets/img/yut_small_backward.png").toExternalForm());
        Image image2 = new Image(getClass().getResource("/assets/img/yut_small_upward.png").toExternalForm());
        Image image3 = new Image(getClass().getResource("/assets/img/yut_small_backdo.png").toExternalForm());

        if (yut[1] == 0) {
            this.firtstYut.setImage(image3);
        } else {
            this.firtstYut.setImage(image2);
        }

        if (yut[2] == 0) {
            this.secondYut.setImage(image);
        } else {
            this.secondYut.setImage(image2);
        }

        if (yut[3] == 0) {
            this.thirdYut.setImage(image);
        } else {
            this.thirdYut.setImage(image2);
        }

        if (yut[4] == 0) {
            this.fourthYut.setImage(image);
        } else {
            this.fourthYut.setImage(image2);
        }
    }

    @Override
    public void addRandomThrowButtonListener(EventHandler<ActionEvent> listener) {
        controlPanel.getRandomButton().setOnAction(listener);
    }

    @Override
    public void addSelectedThrowButtonListener(EventHandler<ActionEvent> listener) {
        controlPanel.getSelectButton().setOnAction(listener);
    }

    @Override
    public void addBackButtonListener(EventHandler<ActionEvent> listener) {
        backButton.setOnAction(listener);
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
        controlPanel.getGoalButton().setDisable(!visible);
    }

    @Override
    public Map<Integer, ClickableNodeFX> getNodeMap() {
        return nodeMap;
    }
}
