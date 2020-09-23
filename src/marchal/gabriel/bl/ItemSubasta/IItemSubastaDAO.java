package marchal.gabriel.bl.ItemSubasta;

import java.util.ArrayList;

public interface IItemSubastaDAO {
    public ArrayList<ItemSubasta> getMisItemes(String email) throws Exception;

    public int registrarItemSubasta(ItemSubasta itemSubasta) throws Exception;
    public void registrarImagenesItemes(int iditem, String imagen) throws Exception;
    public ArrayList<String> retornarImagenesItemes(int iditem) throws Exception;
    public int getNextiditemximagen() throws Exception;
    public int getNextItemId() throws Exception;
    public ItemSubasta retornarFullItem(ItemSubasta item) throws Exception;
    public String retornarEmailVendedorItem(ItemSubasta item) throws Exception;
}
