package com.yut.ui.javaFX;

import com.yut.controller.view_interfaces.GameScreenInterface;
import com.yut.model.Yut;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
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
    private static int defaultPieceCount = 0;

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

    private VBox controlPanel;
    private Button backButton;
    private TextArea deckDisplayArea;

    private StackPane layeredBoard = new StackPane();

    public GameScreenFX(int boardType, int playerCount, Runnable onBack) {
        this.boardType = boardType;
        this.playerCount = playerCount;

        HBox topPanel = new HBox();
        topPanel.setPadding(new Insets(10, 15, 10, 15));
        backButton = new Button("\u2190 \uC2DC\uC791 \uD654\uBA74\uC73C\uB85C");
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
        controlPanel = new VBox(10);
        controlPanel.setPadding(new Insets(10));
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
        Label statusLabel = new Label("\uB0A8\uC740 \uB9D0 \uAC1C\uC218 \uD45C\uC2DC");
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
            sb.append("(\uB370\uD06C\uC774 \uBE44\uC5B4 \uC788\uC2B5\uB2C8\uB2E4)");
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

        PieceFX piece = new PieceFX(playerColors[playerID - 1], pieceNumber);
        StackPane.setMargin(piece, new Insets(node.getNodeY(), 0, 0, node.getNodeX()));
        layeredBoard.getChildren().add(piece);
    }

    @Override
    public void deletePiece(int nodeID) {
        // Simplified: in actual implementation, track and remove node's attached piece
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
        previewCircle = new Circle(PreviewCircleFX.radius, playerColors[playerID - 1]);
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
        selectRectangle = new Rectangle(SelectRectangleFX.radius * 2, SelectRectangleFX.radius * 2);
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
        // depends on ControlPanelFX component implementation
    }

    // Remaining interface methods (add listeners, etc.) can be added here as needed
}
