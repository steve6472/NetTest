package steve6472.netest;

import net.querz.nbt.tag.CompoundTag;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/27/2021
 * Project: NetTest
 *
 ***********************/
public interface ISaveable
{
	CompoundTag write();
	void read(CompoundTag tag);
}
