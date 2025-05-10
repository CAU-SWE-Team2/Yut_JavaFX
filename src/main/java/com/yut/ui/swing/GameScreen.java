package com.yut.ui.swing;
import com.yut.controller.view_interfaces.GameScreenInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;


import java.util.HashMap;
import java.util.Map;

public class GameScreen extends JPanel implements GameScreenInterface {
    private List<Piece> pieceList = new ArrayList<>();

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

    private PreviewCircle previewCircle = null;
    private SelectRectangle selectRectangle = null;

    private ControlPanel controlPanel;
    private JButton backButton;

    private JLayeredPane layeredBoard = new JLayeredPane();

    /*
    // 말이 말판에 올라갈 때
    public void addPieceToBoard(int playerId, int totalPieceCount) {
        int entered = piecesEntered.getOrDefault(playerId, 0);
        piecesEntered.put(playerId, entered + 1);
        updateBottom(playerId, totalPieceCount);
    }

    // 하단 상태창(PlayerCanvas) 갱신
    private void updateBottom(int playerID, int totalPieceCount) {
        int entered = piecesEntered.getOrDefault(playerID, 0);
        int remaining = totalPieceCount - entered;

        PlayerCanvas canvas = playerCanvases.get(playerID);
        if (canvas != null) {
            canvas.setPieceCount(remaining);
        }
    }
*/

    public GameScreen(MainFrame frame, int boardType, int playerCount) {
        this.frame = frame;
        this.boardType = boardType;
        this.playerCount = playerCount;
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButton = new JButton("← 시작 화면으로");
        topPanel.add(backButton);
        add(topPanel, BorderLayout.NORTH);

        backButton.addActionListener(e -> frame.showStart());

        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        //apparently this is required
        layeredBoard.setLayout(null);

        layeredBoard.setPreferredSize(new Dimension(400, 400));

        BoardCanvas boardCanvas = new BoardCanvas(boardType);

        boardCanvas.setBounds(0, 0, 400, 400); // 위치 고정
        boardCanvas.setEnabled(false);
        boardCanvas.setInteractive(true);

        layeredBoard.add(boardCanvas, JLayeredPane.DEFAULT_LAYER);
        boardCanvas.setGameScreen(this);
        this.boardCanvas = boardCanvas;


        //make sure yut board has been generated fully before doing anything with nodes
        layeredBoard.repaint(); //force paint
        Toolkit.getDefaultToolkit().sync(); //ensures paint completes

        //Delay node map setup until after paintComponent has run
        SwingUtilities.invokeLater(() -> {
            clickableNodes = boardCanvas.getClickableNodes();
            for (ClickableNode node : clickableNodes) {
                nodeMap.put(node.nodeID, node);

                int radius = 18; // or get it from the node
                int x = node.getNodeX() - radius;
                int y = node.getNodeY() - radius;
                node.setBounds(x, y, radius * 2, radius * 2);
                layeredBoard.add(node, JLayeredPane.POPUP_LAYER);
            }
            System.out.println("Deferred node map loaded: " + nodeMap.keySet());
        });


        clickableNodes = boardCanvas.getClickableNodes();

        //generate Map of Nodes
        for (ClickableNode node : clickableNodes){
            nodeMap.put(node.nodeID, node);
        }
        //For testing
        /*
        for (int i = playerCount - 1; i >= 0; i--) {
            Color color = playerColors[i % playerColors.length];
            Piece piece = new Piece(boardCanvas.getStartX(), boardCanvas.getStartY(), color, 1);
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
                    if (node.containsInRange(e.getX(), e.getY())) {
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
         */

        JPanel boardPanel = new JPanel();
        boardPanel.add(layeredBoard);
        controlPanel = new ControlPanel();

        centerPanel.add(boardPanel);
        centerPanel.add(controlPanel);
        add(centerPanel, BorderLayout.CENTER);

        //create BottomPanel
        initBottomPanel();

    }
    //related to "For Testing"
    /*
    public boolean isAwaitingMove() {
        return awaitingMove && selectedPieceId != null;
    }
    */

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

    //piece를 클릭한 노드의 중심으로 이동
    public void movePiece (int nodeID){
        ClickableNode node = boardCanvas.getNodeById(nodeID);
        int cx = node.x - pieceList.get(selectedPieceId).getWidth() / 2;
        int cy = node.y - pieceList.get(selectedPieceId).getHeight() / 2;
        pieceList.get(selectedPieceId).setLocation(cx, cy);

        //setter in Piece class
        pieceList.get(selectedPieceId).setCoords(cx, cy);
    }

    public class SelectRectangle extends JComponent{
        public static final int radius = 18;
        private int x;
        private int y;

        SelectRectangle(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.BLACK); // use the stored color
            g2.setStroke(new BasicStroke(3));
            g2.drawRect(0, 0, radius * 2, radius * 2); // draw relative to the component
        }
    }

    public class PreviewCircle extends JComponent{
        public static final int radius = 18;
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
            g2.setStroke(new BasicStroke(5));
            g2.drawOval(0, 0, radius * 2, radius * 2); // draw relative to the component
        }
    }

    public void showMovePreview(int nodeID, int playerID){
        ClickableNode node = nodeMap.get(nodeID);
        int x = node.getNodeX() - PreviewCircle.radius;
        int y = node.getNodeY() - PreviewCircle.radius;
        previewCircle = new PreviewCircle(x, y, playerColors[playerID]);
        previewCircle.setBounds(x, y, PreviewCircle.radius*2, PreviewCircle.radius*2);
        node.setPreviewPiece(true);
        layeredBoard.add(previewCircle);
    }

    public void deleteMovePreview(){
        if (previewCircle == null) {
            throw new RuntimeException("Preview Circle cannot be deleted because it doesn't exist");
        }
        else {
            layeredBoard.remove(previewCircle);
            previewCircle = null;
            layeredBoard.revalidate();
            layeredBoard.repaint();
        }
    }

    public void drawPiece (int nodeID, int playerID, int pieceNumber){
        ClickableNode node = nodeMap.get(nodeID);
        if (node == null) throw new RuntimeException("Node does not exist");

        Piece piece = new Piece(node.getNodeX() - Piece.radius, node.getNodeY() - Piece.radius, playerColors[playerID - 1], pieceNumber);
        piece.setBounds(node.getNodeX() - Piece.radius, node.getNodeY() - Piece.radius, Piece.radius*2, Piece.radius*2);
        layeredBoard.add(piece, JLayeredPane.PALETTE_LAYER);
        node.setOnNodePiece(piece);
        layeredBoard.revalidate();
        layeredBoard.repaint();
        System.out.println("Drew piece at node " + nodeID + " (" + node.getNodeX() + ", " + node.getNodeY() + ")");

    }

    public void deletePiece (int nodeID){
        if (nodeMap.get(nodeID) == null) throw new RuntimeException("There are no pieces to delete on node" + nodeID);
        ClickableNode node = nodeMap.get(nodeID);
        Piece piece = node.getOnNodePiece();
        node.setOnNodePiece(null);
        layeredBoard.remove(piece);
        layeredBoard.revalidate();
        layeredBoard.repaint();
    }

    //힌트가 있는 노드인지 = 1, 빈 노드인지 = 2, 말이 있는 노드인지 = 3;
    public int getNodeState(int nodeID){
        return nodeMap.get(nodeID).getState();
    }

    //playerID STARTS FROM 1, NOT 0
    public void updatePlayerCanvas(int playerID, int pieceCount){
        PlayerCanvas playercanvas = playerCanvases.get(playerID);
        playercanvas.setPieceCount(pieceCount);
        playercanvas.repaint();
    }

    public void select(int nodeID){
        if(selectRectangle != null) {
            layeredBoard.remove(selectRectangle);
            layeredBoard.revalidate();
            layeredBoard.repaint();
        }
        ClickableNode node = nodeMap.get(nodeID);
        int x = node.getNodeX() - PreviewCircle.radius;
        int y = node.getNodeY() - PreviewCircle.radius;
        selectRectangle = new SelectRectangle(x, y);
        selectRectangle.setBounds(x, y, PreviewCircle.radius*2, PreviewCircle.radius*2);
        node.setPreviewPiece(true);
        layeredBoard.add(selectRectangle);
    }

    //highlights the yut given by backend. Updates display on the control panel
    public void updateRandomResult(int yut){
        controlPanel.highlightYutButton(yut);
    }

    public void addRandomThrowButtonListener(ActionListener listener){
        controlPanel.getRandomButton().addActionListener(listener);
    }

    public void addSelectedThrowButtonListener(int index, ActionListener listener){
        controlPanel.getYutButtons()[index].addActionListener(listener);
    }


    public void addBackButtonListener(ActionListener listener){
        backButton.addActionListener(listener);
    }

    public void addNodeClickListener(ClickableNode node, MouseListener listener){
        node.addMouseListener(listener);
    }

    public Map<Integer, ClickableNode>getNodeMap(){
        return nodeMap;
    }

}
