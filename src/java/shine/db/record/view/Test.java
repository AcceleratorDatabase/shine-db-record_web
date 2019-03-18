/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shine.db.record.view;

import shine.db.record.api.IocAPI;
import shine.db.record.api.ServerAPI;
import shine.db.record.entity.Ioc;
import shine.db.record.entity.Server;

/**
 *
 * @author Lvhuihui
 */
public class Test {
    public static void main(String args[]){
    
       Server server=new ServerAPI().getByIP("10.40.18.43");
       System.out.println(server.getIocList().size());
       Ioc ioc=new IocAPI().getByName("iocfirst");
       System.out.println(ioc.getRecordList().size());
       System.out.println(ioc.getRecordList());
    }
}
