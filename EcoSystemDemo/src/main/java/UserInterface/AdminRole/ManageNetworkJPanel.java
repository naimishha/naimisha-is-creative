package UserInterface.AdminRole;

import Business.EcoSystem;
import Business.Network.Network;
import javax.swing.*;
import java.awt.*;

public class ManageNetworkJPanel extends JPanel {
    private JPanel container;
    private EcoSystem system;
    
    public ManageNetworkJPanel(JPanel container, EcoSystem system) {
        this.container = container;
        this.system = system;
        initComponents();
    }
    
    private void initComponents() {
        // Add network management components here
    }
} 