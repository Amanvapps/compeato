package com.appinnovates.campeat.services.MenuService;

public class MenuStore {

    /*public List<BranchMenuModel> saveData(List<ParseObject> objects) {
        DealStore dealStore = new DealStore();
        List<BranchMenuModel> branchMenuModelList = new ArrayList<>();
        if (objects != null && objects.size() > 0) {
            for (ParseObject parseObject : objects) {
                BranchMenuModel branchMenuModel = new BranchMenuModel();
                branchMenuModel.setMenu_name((String) parseObject.get("menu_name"));
                branchMenuModel.setIs_available_yn((String) parseObject.get("is_available_yn"));
                branchMenuModel.setMenu_extra_details((String) parseObject.get("menu_extra_details"));
                branchMenuModel.setId(parseObject.getObjectId());
                try {
                    int price = (int) parseObject.get("price");
                    branchMenuModel.setPrice(String.valueOf(price));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ParseObject branchParseObject = parseObject.getParseObject("branch_id");
                BranchDealsModel branchModel = dealStore.saveBranchData(branchParseObject);
                branchMenuModel.setBranchModel(branchModel);
                List<ParseObject> menuParseObject = parseObject.getList("categories");
                List<MenuTypeModel> menuTypeModel = saveMenuData(menuParseObject);
                branchMenuModel.setMenu_type(menuTypeModel);
                branchMenuModelList.add(branchMenuModel);
            }
        }
        return branchMenuModelList;
    }

    List<MenuTypeModel> saveMenuData(List<ParseObject> menuParseObject) {
        List<MenuTypeModel> menuTypeModelList = new ArrayList<>();
        for (int i = 0 ; i< menuParseObject.size();i++) {
            MenuTypeModel menuTypeModel = null;
            menuTypeModel = new MenuTypeModel();
            menuTypeModel.setId(menuParseObject.get(i).getObjectId());
            menuTypeModel.setMenu_type_name((String) menuParseObject.get(i).get("menu_type_name"));
            menuTypeModelList.add(menuTypeModel);
        }

        return menuTypeModelList;
    }*/

}
