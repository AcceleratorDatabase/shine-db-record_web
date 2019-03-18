/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shine.db.record.view;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.component.panel.Panel;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import shine.db.record.api.IocAPI;
import shine.db.record.api.ServerAPI;
import shine.db.record.api.RecordAPI;
import shine.db.record.entity.FieldValue;
import shine.db.record.entity.Ioc;
import shine.db.record.entity.Record;
import shine.db.record.entity.Server;

/**
 *
 * @author Lvhuihui
 */
@Named(value = "navbarView")
@ViewScoped
public class NavbarView implements Serializable {

    private TreeNode root;
    private TreeNode selectedNode;
    private List<Ioc> iocList;
    private List<Record> recordList;
    private List<FieldValue> fieldValueList;
    private List<Record> connectedRecordList;
    private List<Record> unConnectedRecordList;
    private Panel serverPanel;
    private Panel iocPanel;
    private Panel recordPanel;
    private Server selectedServer;
    private Ioc selectedIOC;
    private Record selectedRecord;

    public List<Record> getConnectedRecordList() {
        return connectedRecordList;
    }

    public void setConnectedRecordList(List<Record> connectedRecordList) {
        this.connectedRecordList = connectedRecordList;
    }

    public List<Record> getUnConnectedRecordList() {
        return unConnectedRecordList;
    }

    public void setUnConnectedRecordList(List<Record> unConnectedRecordList) {
        this.unConnectedRecordList = unConnectedRecordList;
    }
   
    public Server getSelectedServer() {
        return selectedServer;
    }

    public void setSelectedServer(Server selectedServer) {
        this.selectedServer = selectedServer;
    }

    public Ioc getSelectedIOC() {
        return selectedIOC;
    }

    public void setSelectedIOC(Ioc selectedIOC) {
        this.selectedIOC = selectedIOC;
    }

    public Record getSelectedRecord() {
        return selectedRecord;
    }

    public void setSelectedRecord(Record selectedRecord) {
        this.selectedRecord = selectedRecord;
    }
   
    public Panel getServerPanel() {
        return serverPanel;
    }

    public void setServerPanel(Panel serverPanel) {
        this.serverPanel = serverPanel;
    }

    public Panel getIocPanel() {
        return iocPanel;
    }

    public void setIocPanel(Panel iocPanel) {
        this.iocPanel = iocPanel;
    }

    public Panel getRecordPanel() {
        return recordPanel;
    }

    public void setRecordPanel(Panel recordPanel) {
        this.recordPanel = recordPanel;
    }

    public List<FieldValue> getFieldValueList() {
        return fieldValueList;
    }

    public void setFieldValueList(List<FieldValue> fieldValueList) {
        this.fieldValueList = fieldValueList;
    }

    public List<Record> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<Record> recordList) {
        this.recordList = recordList;
    }

    public List<Ioc> getIocList() {
        return this.iocList;
    }

    public void setIocList(List<Ioc> iocList) {
        this.iocList = iocList;
    }

    public NavbarView() {
    }

    @PostConstruct
    public void init() {
        ServerAPI serverAPI = new ServerAPI();
        //System.out.println(server_facade);
        root = new DefaultTreeNode("Root", null);
        List<Server> server_list = serverAPI.getAllServer();
        //System.out.println(server_list);
        for (int i = 0; i < server_list.size(); i++) {
            //TreeNode node=new DefaultTreeNode(server_list.get(i).getIp(),root);           
            Server server = server_list.get(i);
            TreeNode node = new DefaultTreeNode(server.getIp(), root);
            List<Ioc> ioc_list = server.getIocList();
            for (int j = 0; j < ioc_list.size(); j++) {
                Ioc ioc = ioc_list.get(j);
                TreeNode iocnode = new DefaultTreeNode(ioc.getName(), node);
                List<Record> record_list = ioc.getRecordList();
                for (int t = 0; t < record_list.size(); t++) {
                    Record record = record_list.get(t);
                    TreeNode recordnode = new DefaultTreeNode(record.getName(), iocnode);
                }
            }
        }

    }

    public TreeNode getRoot() {
        return root;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public void onNodeSelect(NodeSelectEvent event) {
        if (this.selectedNode.getParent().equals(root)) {
            this.selectedServer = new ServerAPI().getByIP(this.selectedNode.toString());
            this.iocList = this.selectedServer.getIocList();
           // System.out.println(this.iocList.size());          
            this.serverPanel.setVisible(true);
            this.iocPanel.setVisible(false);
            this.recordPanel.setVisible(false);
           

        } else if (this.selectedNode.getParent().getParent().equals(root)) {
            this.selectedIOC = new IocAPI().getByName(this.selectedNode.toString());
            this.recordList = this.selectedIOC.getRecordList();
            this.connectedRecordList=new IocAPI().getConnectedRecordList(selectedIOC);
            //System.out.println(this.connectedRecordList);          
            this.serverPanel.setVisible(false);
            this.iocPanel.setVisible(true);
            this.recordPanel.setVisible(false);           
            //this.iocList.clear();        

        } else if (this.selectedNode.getParent().getParent().getParent().equals(root)) {
            this.selectedRecord = new RecordAPI().getByName(this.selectedNode.toString());
            this.fieldValueList = this.selectedRecord.getFieldValueList();
            this.recordPanel.setVisible(true);
            this.serverPanel.setVisible(false);
            this.iocPanel.setVisible(false);
        }

    }
}
