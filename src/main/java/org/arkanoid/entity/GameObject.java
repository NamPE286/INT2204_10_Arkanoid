package org.arkanoid.entity;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;

import java.util.ArrayList;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

/**
 * Abstract base class for all game objects in the game world.
 * <p>
 * Handles entity creation, spawning, input initialization, and per-frame updates.
 */
abstract public class GameObject {
    protected Entity entity = null;
    List<Entity> collisionSubscribers = new ArrayList<>();

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
    public void onUpdate(double deltaTime) {
        for (var e : collisionSubscribers) {
            if (entity.getBoundingBoxComponent().isCollidingWith(e.getBoundingBoxComponent())) {
                onCollisionWith(e);
            }
        }
    }

    /**
     * Adds a GameObject as a collision subscriber.
     * When this object collides with another, all subscribers can be notified or react accordingly.
     *
     * @param o the GameObject to subscribe for collision events
     * @return this GameObject (for method chaining)
     */
    public GameObject addCollisionSubscriber(GameObject o) {
        collisionSubscribers.add(o.getEntity());
        return this;
    }

    /**
     * Called when this GameObject collides with another entity.
     * <p>
     * The default implementation does nothing. Override this method in subclasses to define custom collision behavior.
     *
     * @param e the Entity that this object has collided with
     */
    public void onCollisionWith(Entity e) {
        // Default implementation does nothing
    }

    /**
     * Gets the current X position of this GameObject.
     *
     * @return the X coordinate
     */
    public double getX() {
        return entity.getX();
    }

    /**
     * Gets the current Y position of this GameObject.
     *
     * @return the Y coordinate
     */
    public double getY() {
        return entity.getY();
    }

    /**
     * Sets the X position of this GameObject.
     *
     * @param x the new X coordinate
     */
    public void setX(double x) {
        entity.setX(x);
    }

    /**
     * Sets the Y position of this GameObject.
     *
     * @param y the new Y coordinate
     */
    public void setY(double y) {
        entity.setY(y);
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
