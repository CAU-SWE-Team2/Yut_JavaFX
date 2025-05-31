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

    public GameScreenFX(int boardType, int playerCount, Runnable onBack) {
        this.boardType = boardType;
        this.playerCount = playerCount;
        this.onBack = onBack;

        HBox topPanel = new HBox();
        topPanel.setPadding(new Insets(10, 15, 10, 15));
        Button backButton = new Button("← 시작 화면으로");
        backButton.setPrefSize(150, 40);
        backButton.setOnAction(e -> onBack.run());
        topPanel.getChildren().add(backButton);
        setTop(topPanel);

        boardCanvas = new BoardCanvasFX(boardType);
        clickableNodes = boardCanvas.getClickableNodes();
        for (ClickableNodeFX node : clickableNodes) {
            nodeMap.put(node.getNodeID(), node);
        }
        layeredBoard.getChildren().add(boardCanvas);

        HBox centerPanel = new HBox(10);
        centerPanel.setAlignment(Pos.CENTER);

        controlPanel = new ControlPanelFX();

        deckDisplayArea = new TextArea();
        deckDisplayArea.setPrefRowCount(5);
        deckDisplayArea.setWrapText(true);
        deckDisplayArea.setEditable(false);
        controlPanel.getChildren().add(deckDisplayArea);

        centerPanel.getChildren().addAll(layeredBoard, controlPanel);
        setCenter(centerPanel);

        initBottomPanel();
    }

    private void initBottomPanel() {
        VBox bottomPanel = new VBox(10);
        Label statusLabel = new Label("남은 말 개수 표시");
        statusLabel.setFont(new Font("SansSerif", 14));
        bottomPanel.getChildren().add(statusLabel);

        HBox playersBox = new HBox(10);
        for (int i = 1; i <= playerCount; i++) {
            piecesEntered.put(i, 0);
            Color color = playerColors[(i - 1) % playerColors.length];
            PlayerCanvasFX player = new PlayerCanvasFX(i, defaultPieceCount, color);
            playerCanvases.put(i, player);
            playersBox.getChildren().add(player);
        }
        bottomPanel.getChildren().add(playersBox);
        setBottom(bottomPanel);
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
        StackPane.setMargin(piece, new Insets(node.getNodeY(), 0, 0, node.getNodeX()));
        layeredBoard.getChildren().add(piece);
    }

    @Override
    public void deletePiece(int nodeID) {
        // 제거 로직 필요시 구현
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
        if (yut == 4 || yut == 5) {
            controlPanel.getMoveNewPieceButton().setDisable(true);
        } else {
            controlPanel.getMoveNewPieceButton().setDisable(false);
        }
    }

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
        // 이미 생성자에서 처리됨. 필요시 별도 처리 가능.
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
}
