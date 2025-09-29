package org.arkanoid.entity;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

/**
 * Abstract base class for all game objects in the game world.
 * <p>
 * Handles entity creation, spawning, input initialization, and per-frame updates.
 */
abstract public class GameObject {

    /** The FXGL entity associated with this game object. */
    protected Entity entity = null;

    /**
     * Returns the FXGL entity associated with this game object.
     *
     * @return the entity instance
     */
    public Entity getEntity() {
        return entity;
    }

    /**
     * Adds the entity to the game world, making it active in the scene.
     */
    protected void spawn() {
        getGameWorld().addEntity(entity);
    }

    /**
     * Creates the FXGL entity for this game object.
     * <p>
     * Subclasses must implement this method to define how their entity
     * is constructed and initialized.
     *
     * @param spawnData the spawn data used to initialize the entity
     * @return the newly created entity
     */
    protected abstract Entity createEntity(SpawnData spawnData);

    /**
     * Initializes input controls for this game object.
     * <p>
     * Subclasses can override this method to bind keys or actions.
     * The default implementation does nothing.
     */
    protected void initInput() {
        // Default implementation does nothing
    }

    /**
     * Called once per frame to update the object's state.
     * <p>
     * Subclasses should override this method to implement per-frame
     * logic, such as movement, animations, or other behavior.
     *
     * @param deltaTime the time elapsed (in seconds) since the last frame
     */
    public void update(double deltaTime) {
        // Default implementation does nothing
    }

    /**
     * Constructs a new game object at the default location (0, 0).
     * <p>
     * Initializes the entity, spawns it in the game world, and sets up input.
     */
    public GameObject() {
        entity = createEntity(new SpawnData());
        spawn();
        initInput();
    }

    /**
     * Constructs a new game object at the specified coordinates.
     * <p>
     * Initializes the entity with the given position, spawns it in the
     * game world, and sets up input.
     *
     * @param x the x-coordinate for the entity's initial position
     * @param y the y-coordinate for the entity's initial position
     */
    public GameObject(int x, int y) {
        entity = createEntity(new SpawnData(x, y));
        spawn();
        initInput();
    }
}
