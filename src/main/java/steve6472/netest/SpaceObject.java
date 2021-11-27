package steve6472.netest;

import org.joml.Vector2d;

import java.util.UUID;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 11/27/2021
 * Project: NetTest
 *
 ***********************/
public abstract class SpaceObject implements ISaveable
{
	public UUID id;
	public Vector2d position;

	public SpaceObject(UUID id)
	{
		this.id = id;
	}

	public abstract void tick();
}
