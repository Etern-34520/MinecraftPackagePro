package io;

import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeItem.TreeModificationEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tree_ {
	static TreeItem<String> Null=new TreeItem<String>();
	String rootName;
	public HashMap<TreeItem<String>,File> fileMap=new HashMap<TreeItem<String>,File>();
	TreeItem<String> root = null;
	boolean reflash=false;
	private List<TreeItem<String>> expanded;
	//TreeItem<String> addItem=new TreeItem<String>();
	public TreeItem<String> readCreate(String path,TreeItem<String> item) {
		try {
			List<String> files = new ArrayList<String>();
			File file = new File(path);
			//System.out.println(file.listFiles());
			rootName=path;

			File[] tempList = file.listFiles();
			TreeItem<String> rootItem=new TreeItem<String>(path);

			/*
		treeview.addEventFilter(MouseEvent.MOUSE_CLICKED ,new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				//Node node=event.getPickResult().getIntersectedNode();
				System.out.println("a");
				TreeItem<String> rootItem=(TreeItem<String>)treeview.getSelectionModel().getSelectedItem();
				//ReadCreate(treeview,p,rootItem);
			}
		}
				);
			 */
			if(item!=null) {
				rootItem=item;
				item.getChildren().removeAll(item.getChildren());
			}

			for (int i = 0; i < tempList.length; i++) {
				TreeItem<String> addItem=new TreeItem<String>(tempList[i].getName());
				rootItem.getChildren().add(addItem);
				File file1=tempList[i];


				fileMap.put(addItem, file1);
				if (tempList[i].isFile()) {
					files.add(tempList[i].toString());
					/*
				addItem.addEventHandler(TreeItem., new EventHandler<TreeModificationEvent<String>>() {

					@Override
					public void handle(TreeModificationEvent<String> arg0) {
						// TODO �Զ����ɵķ������
						System.out.println(arg0.getTreeItem());
					}

				});
					 */
				} else if (tempList[i].isDirectory()) {
					String add=tempList[i].toString();
					files.add(add);
					addItem.getChildren().add(Null);
					if(tempList[i].list()!=null&&tempList[i].list().length>0) {
						addItem.addEventHandler(TreeItem.branchExpandedEvent(), new EventHandler<TreeModificationEvent<String>>() {
							@Override
							public void handle(TreeModificationEvent<String> event) {
								TreeItem<String> newRoot=event.getTreeItem();
								if (newRoot.getChildren().size()==1&&newRoot.getChildren().get(0).equals(Null)) {
									readCreate(file1.getPath()+"\\",newRoot);
									newRoot.getChildren().remove(0);
									//System.out.println(fileMap.get(newRoot));
								}
								//System.out.println(getExpandedDoctments(addItem));
								//System.out.println(rootName);
								//System.out.println(getPath(newRoot));
							}
						});
					} else {
						addItem.getChildren().remove(0);
					}


					if (reflash&&forEquals(expanded, addItem)) {
						addItem.setExpanded(true);
					}
				}
			}
			if (root==null) {
				root=rootItem;
				fileMap.put(root, file);
			}
			rootItem.setExpanded(true);
			return rootItem;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	private List<TreeItem<String>> getExpandedDoctments(TreeItem<String> root){
		List<TreeItem<String>> expanded=new ArrayList<TreeItem<String>>();
		expanded.add(this.root);
		for (int i = 0; i < root.getChildren().size(); i++) {
			TreeItem<String> temp=root.getChildren().get(i);
				if (temp.isExpanded()) {
					expanded.add(temp);
					List<TreeItem<String>> childenExpanded=getExpandedDoctments(temp);
					for (int j = 0; j < childenExpanded.size(); j++) {
						expanded.add(childenExpanded.get(j));
					}
				} else if (!temp.isLeaf()&&temp.getChildren().size()!=1&&temp.getChildren().get(0)!=Null){
					temp.getChildren().removeAll(temp.getChildren());
					temp.getChildren().add(Null);
				}
			
		}
		this.expanded=expanded;
		/*
		for (int i = 0; i < expanded.size(); i++) {
			System.out.println("asd"+expanded.get(i).getValue());
			if (expanded.get(i).getValue()!=null)
				this.expanded.add(expanded.get(i).getValue());
		}
		*/
		return expanded;
	}
	private void reflash(List<TreeItem<String>> expanded) {
		//System.out.println(expanded.get(0));
		for (int i = 0; i < expanded.size(); i++) {
			TreeItem<String> temp = expanded.get(i);
			//System.out.println(fileMap.get(temp));
			temp.getChildren().removeAll(temp.getChildren());
			//System.out.println(fileMap.get(temp).getPath());
			readCreate(fileMap.get(temp).getPath(),temp);
		}
	}
	private boolean forEquals(List<TreeItem<String>> list,TreeItem<String> need){
		for (int i = 0; i < list.size(); i++) {
			TreeItem<String> item = list.get(i);
			if (item.getValue().equals(need.getValue())) {
				//expanded.remove(item);
				return true;
			}
		}
		return false;
	}
	
	public void reflash() {
		reflash=true;
		//System.out.println(root.getChildren());
		//System.out.println(getExpandedDoctments(root));
		List<TreeItem<String>> expanded = getExpandedDoctments(root);
		if (expanded!=null&&root.getChildren()!=null) {
			reflash(expanded);
			//System.out.println("reflash");
		}
		reflash=false;
		//System.out.println(fileMap);
	}
	
	public File getFile(TreeItem<String> item) {
		return fileMap.get(item);
	}
	
}

//}

