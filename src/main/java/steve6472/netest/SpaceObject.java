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
	public final UUID uuid;
	public final Vector2d position;
	public float rotation;

	public SpaceObject(UUID uuid)
	{
		this.uuid = uuid;
		this.position = new Vector2d();
	}

	public abstract void tick();
}
