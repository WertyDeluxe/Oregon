package org.oniteam.oregontrailfx.model;

import java.util.ArrayList;

public class OregonTrail {
    private ArrayList<Achivement> achivements=new ArrayList();
    private TreeAchivement treeAchivement = new TreeAchivement();

    public void insertAllachivementsToArraylist(){
        Achivement a1=new Achivement(1,"un gran comienzo","el jugador por primera vez da un paso en oregon");
        Achivement a2= new Achivement(2,"","");
        Achivement a3= new Achivement(3,"","");
        Achivement a4= new Achivement(4,"","");
        Achivement a5= new Achivement(5,"","");
        Achivement a6= new Achivement(6,"","");
        Achivement a7= new Achivement(7,"","");
        Achivement a8= new Achivement(8,"","");
        Achivement a9= new Achivement(9,"","");
        Achivement a10= new Achivement(10,"","");



        achivements.add(a5);
        achivements.add(a4);
        achivements.add(a3);
        achivements.add(a2);
        achivements.add(a1);
        achivements.add(a6);
        achivements.add(a7);
        achivements.add(a8);
        achivements.add(a9);
        achivements.add(a10);
    }


    public void insertAchivementsToTree(){
        for(int i=0;i<achivements.size();i++){
            NodeAchivement n=new NodeAchivement(achivements.get(i),null,null);
            treeAchivement.insertInAllAchivement(n);
        }
    }
}
