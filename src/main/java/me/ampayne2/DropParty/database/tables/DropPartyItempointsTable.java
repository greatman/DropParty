package me.ampayne2.DropParty.database.tables;

import com.alta189.simplesave.Field;
import com.alta189.simplesave.Id;
import com.alta189.simplesave.Table;

@Table("DropPartyItempoint")
public class DropPartyItempointsTable {

	@Id
	public int id;

	@Field
	public int x;

	@Field
	public int y;

	@Field
	public int z;

	public String toString() {
		return x + ":" + y + ":" + z;
	}

}
