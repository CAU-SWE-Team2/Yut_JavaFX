package com.yut.ui.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import java.util.HashMap;
import java.util.Map;
import com.yut.listener.PieceSelectionListener;

public class GameScreen extends JPanel {
    private List<Piece> pieceList = new ArrayList<>();
    private Map<Integer, Piece> pieceMap = new HashMap<>(); //

    private final Map<Integer, Integer> piecesEntered = new HashMap<>(); // 각 플레이어가 말판에 올려놓은 말의 수
    private final Map<Integer, PlayerCanvas> playerCanvases = new HashMap<>(); // 남은 말 개수 (PlayerCanvas) 저장
    private MainFrame frame;
    private int boardType;
    private int playerCount;

    private static int defaultPieceCount = 0;
    //player 1 is red, 2 id light blue
    Color[] playerColors = {
            new Color(240, 128, 128),
            new Color(176, 224, 230),
            new Color(198, 233, 105),
            new Color(221, 160, 221)
    };

    private List<ClickableNode> clickableNodes;
    private Map<Integer, ClickableNode> nodeMap = new HashMap<>();

    //for moving pieces
    private BoardCanvas boardCanvas;
    private Integer selectedPieceId = null;
    private boolean awaitingMove = false;
    private Piece previewPiece = null;

    private PreviewCircle previewCircle = null;


    // 말이 말판에 올라갈 때
    public void addPieceToBoard(int playerId, int totalPieceCount) {
        int entered = piecesEntered.getOrDefault(playerId, 0);
        piecesEntered.put(playerId, entered + 1);
        updateBottom(playerId, totalPieceCount);
    }

    // 말이 완주할 때

    /*
    public void addFinishedPiece(int playerId, int totalPieceCount) {
        int entered = piecesEntered.getOrDefault(playerId, 0);
        int finished = piecesFinished.getOrDefault(playerId, 0) + 1;
        piecesFinished.put(playerId, finished);

        if (finished > entered) {
            JOptionPane.showMessageDialog(this, "말이 들어오지 않았는데 도착 처리됨 (플레이어 " + playerId + ")", "오류",
                    JOptionPane.WARNING_MESSAGE);
            piecesFinished.put(playerId, entered);
            return;
        }

        if (finished == totalPieceCount) {
            int result = JOptionPane.showOptionDialog(
                    this,
                    "플레이어 " + playerId + " 우승!",
                    "게임 종료",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[] { "게임 종료", "다시 시작" },
                    null);

            if (result == 0) {
                System.exit(0); // 게임 종료
            } else if (result == 1 && frame != null) {
                frame.showGame(boardType, playerCount, pieceCount); // 다시 시작
            }
        }
    }
    */

    // 하단 상태창(PlayerCanvas) 갱신
    private void updateBottom(int playerId, int totalPieceCount) {
        int entered = piecesEntered.getOrDefault(playerId, 0);
        int remaining = totalPieceCount - entered;

        PlayerCanvas canvas = playerCanvases.get(playerId);
        if (canvas != null) {
            canvas.setPieceCount(remaining);
        }
    }

    public GameScreen(MainFrame frame, int boardType, int playerCount) {
        this.frame = frame;
        this.boardType = boardType;
        this.playerCount = playerCount;
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("← 시작 화면으로");
        topPanel.add(backButton);
        add(topPanel, BorderLayout.NORTH);

        backButton.addActionListener(e -> frame.showStart());

        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        JLayeredPane layeredBoard = new JLayeredPane();
        layeredBoard.setPreferredSize(new Dimension(400, 400));

        BoardCanvas boardCanvas = new BoardCanvas(boardType);

        boardCanvas.setBounds(0, 0, 400, 400); // 위치 고정
        boardCanvas.setEnabled(false);
        boardCanvas.setInteractive(true);

        layeredBoard.add(boardCanvas, JLayeredPane.DEFAULT_LAYER);
        boardCanvas.setGameScreen(this);
        this.boardCanvas = boardCanvas;

        clickableNodes = boardCanvas.getClickableNodes();

        //generate Map of Nodes
        for (ClickableNode node : clickableNodes){
            nodeMap.put(node.nodeID, node);
        }

        for (int i = playerCount - 1; i >= 0; i--) {
            Color color = playerColors[i % playerColors.length];
            Piece piece = new Piece(0, boardCanvas.getStartX(), boardCanvas.getStartY(), color);
            System.out.println("x:" + boardCanvas.getStartX());
            piece.setBounds(boardCanvas.getStartX(), boardCanvas.getStartY(), 20, 20); // 말 위치 조정
            pieceList.add(piece);
            //xpiece.setBounds(100 + i * 30, 100, 20, 20);
            //layeredBoard.add(piece, JLayeredPane.PALETTE_LAYER);

            piece.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    selectedPieceId = pieceList.indexOf(piece); // find which piece was clicked
                    awaitingMove = true;
                    System.out.println("Selected piece ID: " + selectedPieceId);
                }
            });
        }

        boardCanvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!boardCanvas.getInteractive()) return; // disable node interaction in preview mode
                for (ClickableNode node : clickableNodes) {
                    if (node.contains(e.getX(), e.getY())) {
                        System.out.println("Clicked on node #" + node.nodeID + "at (" + node.x + ", " + node.y + ")");
                        // TODO: Add game logic here (e.g., move piece)
                        //gameScreen.movePiece(0, node.x, node.y);
                        if (isAwaitingMove()) {
                            movePiece(node.nodeID);
                        }
                        break;
                    }
                }
            }
        });

        JPanel boardPanel = new JPanel();
        boardPanel.add(layeredBoard);
        ControlPanel controlPanel = new ControlPanel();

        centerPanel.add(boardPanel);
        centerPanel.add(controlPanel);
        add(centerPanel, BorderLayout.CENTER);

        //create BottomPanel
        initBottomPanel();

        /*
        // 테스트용: 각 플레이어에게 다양한 수의 말 진입 및 도착 처리
        addPieceToBoard(1, pieceCount);
        addPieceToBoard(1, pieceCount);
        addFinishedPiece(1, pieceCount);

        addPieceToBoard(2, pieceCount);
        addFinishedPiece(2, pieceCount);

        addPieceToBoard(3, pieceCount);
        addPieceToBoard(3, pieceCount);
        addPieceToBoard(3, pieceCount);
        addPieceToBoard(3, pieceCount);

        addFinishedPiece(3, pieceCount);
        addFinishedPiece(3, pieceCount);
        addFinishedPiece(3, pieceCount);
        addFinishedPiece(3, pieceCount);// 말이 4개 이하면 우승
        */

    }

    public boolean isAwaitingMove() {
        return awaitingMove && selectedPieceId != null;
    }

    private void initBottomPanel(){
        JPanel bottomPanelWrapper = new JPanel();
        bottomPanelWrapper.setLayout(new BorderLayout());

        JLabel statusLabel = new JLabel("남은 말 개수 표시", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        bottomPanelWrapper.add(statusLabel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        for (int i = 1; i <= playerCount; i++) {
            piecesEntered.put(i, 0);
            Color color = playerColors[(i - 1) % playerColors.length];
            int remaining = defaultPieceCount;
            PlayerCanvas player = new PlayerCanvas(i, remaining, color);
            playerCanvases.put(i, player);
            bottomPanel.add(player);
        }
        bottomPanelWrapper.add(bottomPanel, BorderLayout.CENTER);
        add(bottomPanelWrapper, BorderLayout.SOUTH);
    }

    public class PreviewCircle extends JComponent{
        private static int radius = 18;
        private int x;
        private int y;
        private Color color;

        PreviewCircle(int x, int y, Color color) {
            this.x = x;
            this.y = y;
            this.color = color;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color); // use the stored color
            g2.drawOval(0, 0, radius * 2, radius * 2); // draw relative to the component
        }
    }


    public void ShowMovePreview(int playerID, int nodeID){
        PlayerCanvas pc = playerCanvases.get(playerID);
        if (pc == null)
            throw new RuntimeException("PlayerCanvas can't be null");
        previewCircle = new PreviewCircle(nodeMap.get(nodeID).getNodeX(), nodeMap.get(nodeID).getNodeY(), pc.getColor());
        previewCircle.setBounds(nodeMap.get(nodeID).getNodeX(), nodeMap.get(nodeID).getNodeY(), 18, 18);
    }

    public void deleteMovePreview(){
        if (previewCircle == null) { throw new RuntimeException("Preview Circle cannot be deleted because it doesn't exist"); }
        else {
            this.remove(previewCircle);
            previewCircle = null;
            this.revalidate();
            this.repaint();
        }
    }

    //piece를 클릭한 노드의 중심으로 이동
    public void movePiece (int nodeID){
        ClickableNode node = boardCanvas.getNodeById(nodeID);
        int cx = node.x - pieceList.get(selectedPieceId).getWidth() / 2;
        int cy = node.y - pieceList.get(selectedPieceId).getHeight() / 2;
        pieceList.get(selectedPieceId).setLocation(cx, cy);

        //setter in Piece class
        pieceList.get(selectedPieceId).setCoords(cx, cy);
    }

    public void move (int playerID, int pieceID, int nodeID){
        ClickableNode node = nodeMap.get(nodeID);
        int x = node.getNodeX();
        int y = node.getNodeY();
        Piece piece;
        if (pieceMap.containsKey(pieceID)) {
            piece = pieceMap.get(pieceID);
            piece.setLocation(x, y);
            piece.setCoords(x, y);
        }
        else {
            piece = new Piece(pieceID, x, y, playerColors[playerID]);
            pieceMap.put(pieceID, piece);
            this.add(piece);
            piece.setBounds(x, y, piece.getRadius()*2, piece.getRadius()*2);
        }

        if (node.getOnNodePiece() != null) {
            if (node.getOnNodePiece().getPlayerID() == playerID) {
                //업기
                //update piece Count
                int totalCount = piece.getCount() + node.getOnNodePiece().getCount();
                piece.setCount(totalCount);
                //delete piece that was originally on nodeID
                deletePiece(node.getOnNodePiece().getPlayerID());
                node.setOnNodePiece(piece);
            }
            else{
                //delete piece that was originally on nodeID
                deletePiece(node.getOnNodePiece().getPlayerID());
                node.setOnNodePiece(piece);
            }
        }
    }

    private void deletePiece (int pieceID){
        if (!pieceMap.containsKey(pieceID)) throw new RuntimeException("Can't find piece with id " + pieceID);
        Piece piece = pieceMap.remove(pieceID);
        this.remove(piece);
        this.revalidate();
        this.repaint();
    }

    //빈 노드인지 = 1, 선택된 말이 있는 노드인지 = 2, 말이 있는 노드인지 = 3;
    public int getNodeState(int nodeID){
        return nodeMap.get(nodeID).getState();
    }

    //for highlighting node when selected
    public void selectNode(int nodeID){
        ClickableNode node = nodeMap.get(nodeID);

    }
}
