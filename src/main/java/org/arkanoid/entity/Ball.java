package org.arkanoid.entity;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import org.arkanoid.entity.brick.HardBrick;
import org.arkanoid.entity.brick.StrongBrick;
import org.arkanoid.component.animation.BrickAnimationComponent;
import org.arkanoid.entity.core.GameObject;
import org.arkanoid.entity.core.MovableObject;
import org.arkanoid.entity.brick.Brick;
import org.arkanoid.game.Game;
import org.arkanoid.utilities.TextureUtils;
import org.arkanoid.manager.SoundManager;
import org.arkanoid.utilities.Vec2Utils;

import java.util.List;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class Ball extends MovableObject {

    private boolean attached = false;
    private boolean isHeldByPaddle = false;
    private Paddle holdingPaddle = null;
    private double holdOffsetX = 0;
    private float lastSpeed = 300;
    @Override
    protected Entity createEntity(SpawnData spawnData) {
        var texture = TextureUtils.crop(FXGL.texture("vaus.png"), 0, 40, 4, 5);

        var e = entityBuilder(spawnData)
                .type(EntityType.BALL)
                .viewWithBBox(texture)
                .build();

        e.setScaleX(2.0);
        e.setScaleY(2.0);

        return e;
    }

    /**
     * Handles collision logic between the ball and another game object.
     *
     * @param e the {@link GameObject} that the ball collided with
     */
    @Override
    public void onCollisionWith(GameObject e) {

        if (attached && e instanceof Paddle) {
            return;
        }

        if (e instanceof Paddle) {
            this.onCollisionWith((Paddle) e);
        } else if (e instanceof Brick) {
            this.onCollisionWith((Brick) e);
        } else if (e instanceof Wall) {
            this.onCollisionWith((Wall) e);
        }
    }

    /**
     * Handles collision logic between the ball and paddle.
     *
     * <p>
     * When colliding with a {@link Paddle}, the bounce angle depends on how far the ball hits from
     * the paddle's center, producing a dynamic reflection. The bounce angle is constrained between
     * 35° and 55°, and the total velocity (speed) of the ball remains constant.
     * </p>
     *
     * @param paddle the {@link Paddle} that the ball collided with
     */
    public void onCollisionWith(Paddle paddle) {
        if (paddle.isCanCatch() && this.getVelocityY() > 0) {
            System.out.println("BALL: Bị Paddle bắt!");

            paddle.useCatch(); // Báo cho paddle biết đã dùng khả năng bắt

            // Save the current speed.
            this.lastSpeed = this.getLinearVelocity().length();
            if (this.lastSpeed == 0) this.lastSpeed = 300; // when speed = 0

            // set velocity = 0.
            this.setLinearVelocity(0, 0);

            this.isHeldByPaddle = true;
            this.holdingPaddle = paddle;


            this.holdOffsetX = this.getX() - paddle.getX();


            FXGL.getGameTimer().runOnceAfter(() -> {
                System.out.println("BALL: AUTO SHOT BALL AFTER 2 SECOND");
                this.isHeldByPaddle = false;
                this.holdingPaddle = null;


                double ballCenterX = getX() + getEntity().getWidth() / 2.0;
                double paddleCenterX = paddle.getX() + paddle.getEntity().getWidth() / 2.0;
                double haftPaddleWidth = paddle.getEntity().getWidth() / 2;

                double distanceBallToPaddleCenter = ballCenterX - paddleCenterX;
                double distanceRatio = distanceBallToPaddleCenter / haftPaddleWidth;
                distanceRatio = Math.clamp(distanceRatio, -1.0, 1.0);
                double nonLinearDistanceRatio = Math.pow(Math.abs(distanceRatio), 0.5);
                if (distanceRatio < 0) nonLinearDistanceRatio *= -1;

                double ANGLE = 90 - (55 * nonLinearDistanceRatio);

                double vx = this.lastSpeed * Math.cos(Math.toRadians(ANGLE));
                double vy = this.lastSpeed * Math.sin(Math.toRadians(ANGLE));

                setLinearVelocity((float) vx, (float) -vy); // fly up.

            }, Duration.seconds(2)); // Execute in 2 seconds.

            return;
        }

        // Do nothing when ball is held.
        if (isHeldByPaddle) {
            return;
        }

        // Normal logic when have no catching effect.
        double vx = this.getVelocityX();
        double vy = this.getVelocityY();

        double ballX = entity.getX();
        double ballY = entity.getY();
        double ballW = entity.getWidth();
        double ballH = entity.getHeight();
        double ballCenterX = ballX + ballW / 2.0;
        double ballCenterY = ballY + ballH / 2.0;

        double paddleX = paddle.getX();
        double paddleY = paddle.getY();
        double paddleW = paddle.getEntity().getWidth();
        double paddleH = paddle.getEntity().getHeight();
        double paddleCenterX = paddleX + paddleW / 2.0;
        double paddleCenterY = paddleY + paddleH / 2.0;

        double overlapLeft = (ballX + ballW) - paddleX;
        double overlapRight = (paddleX + paddleW) - ballX;
        double overlapTop = (ballY + ballH) - paddleY;
        double overlapBottom = (paddleY + paddleH) - ballY;
        double minOverlap = Math.min(Math.min(overlapLeft, overlapRight),
                Math.min(overlapTop, overlapBottom));

        double ballSpeed = this.getLinearVelocity().length();

        double constant = 0.8;

        double distanceX = ballCenterX - paddleCenterX;
        double distanceY = ballCenterY - paddleCenterY;

        double totalHalfW = (ballW / 2.0) + (paddleW / 2.0);
        double totalHalfH = (ballH / 2.0) + (paddleH / 2.0);

        double overlapRatioX = Math.abs(distanceX) / totalHalfW;
        double overlapRatioY = Math.abs(distanceY) / totalHalfH;

        if (minOverlap == overlapTop || (Math.abs(overlapRatioX - overlapRatioY) < constant
                && ballCenterY <= paddleCenterY)) {
            double haftPaddleWidth = paddleW / 2;

            double distanceBallToPaddleCenter = ballCenterX - paddleCenterX;
            double distanceRatio = distanceBallToPaddleCenter / haftPaddleWidth;
            distanceRatio = Math.clamp(distanceRatio, -1.0, 1.0);
            double nonLinearDistanceRatio = Math.pow(Math.abs(distanceRatio),
                    0.5);

            if (distanceRatio < 0) {
                nonLinearDistanceRatio *= -1;
            }

            double ANGLE = 90 - (55
                    * nonLinearDistanceRatio);

            vx = ballSpeed * Math.cos(Math.toRadians(ANGLE));
            vy = ballSpeed * Math.sin(Math.toRadians(ANGLE));
            vy = -vy;

            System.out.println(String.format("(%.3f, %.3f)", vx, vy));
            System.out.println("Collide with Paddle");

            SoundManager.play("ball_hit_1.wav");
        } else if (minOverlap == overlapRight) {
            vx = Math.abs(vx) + 5;
            setPosition((int) ballX + 10, (int) ballY);
        } else if (minOverlap == overlapLeft) {

            setPosition((int) ballX - 10, (int) ballY);
            vx = -(Math.abs(vx) + 5);
        }

        setLinearVelocity((float) vx, (float) vy);

    }

    /**
     * Handles collision logic between the ball and brick.
     *
     * <p>
     * When colliding with a {@link Brick}, the ball simply reverses its velocity along the axis of
     * collision and triggers the brick’s destruction.
     * </p>
     *
     * @param brick the {@link Brick} that the ball collided with
     */
    public void onCollisionWith(Brick brick) {
        Vec2 newVelocity = Vec2Utils.flip(this.getLinearVelocity(), this, brick);
        setLinearVelocity(newVelocity.x, newVelocity.y);

        System.out.println(brick.getHealth());

        if (brick.getHealth() > 1) {
            SoundManager.play("ball_hit.wav");
        } else {
            SoundManager.play("ball_hit_2.wav");
        }

        if (brick instanceof HardBrick || brick instanceof StrongBrick) {
            brick.getEntity().getComponentOptional(BrickAnimationComponent.class)
                    .ifPresent(BrickAnimationComponent::playHitAnimation);
        }

        System.out.println("Collide with brick");
        brick.breakBrick();
    }

    /**
     * Handles collision logic between the ball and wall.
     *
     * <p>
     * When colliding with a {@link Wall}, the ball simply reverses its velocity along the axis of
     * collision and triggers the brick’s destruction.
     * </p>
     *
     * @param wall the {@link Wall} that the ball collided with
     */
    public void onCollisionWith(Wall wall) {
        Vec2 newVelocity = Vec2Utils.flip(this.getLinearVelocity(), this, wall);
        setLinearVelocity(newVelocity.x, newVelocity.y);

        System.out.println("Collide with wall");
    }

    /**
     * Constructs a new ball at the default position (0, 0).
     */
    public Ball() {
        super();
    }

    /**
     * Constructs a new ball at the specified coordinates.
     *
     * @param x the x-coordinate for the ball's initial position
     * @param y the y-coordinate for the ball's initial position
     */
    public Ball(int x, int y) {
        super(x, y);
        spawn();
    }


    public void setAttached(boolean value) {
        this.attached = value;
    }

    public boolean isAttached() {
        return attached;
    }

    public void createTwins() {
        Point2D spawnPosition = this.getEntity().getCenter();
        Vec2 curVec = this.getLinearVelocity();

        // Tỉ lệ tốc độ tối thiểu.
        final float MIN_VERTICAL = 0.15f;
        float ballSpeed = this.getLinearVelocity().length();
        float minVy =  ballSpeed * MIN_VERTICAL;

        float vx = curVec.x;
        float vy = curVec.y;

        // Tạo ra hai quả bóng tạo với bóng cũ một góc 15 độ
        double angleRad = Math.toRadians(15);
        float cosAngle = (float) Math.cos(angleRad);
        float sinAngle = (float) Math.sin(angleRad);

        // Công thức xoay của toán :
        // new x = x.cos(alpha) - y.sin(alpha)
        // new y = x.sin(alpha) + y.cos(alpha)


        // xoay bên phải (15 độ so với trục hoành)
        float vx1 = vx * cosAngle - vy * sinAngle;
        float vy1 = vx * sinAngle + vy * cosAngle;

        // xoay bên trái (-15 độ so với trục hoành)
        // cos(15) = cos(-15), sin(-15) = sin(-15)
        float vx2 = vx * cosAngle + vy * sinAngle;
        float vy2 = -vx * sinAngle + vy * cosAngle;

        // Kiểm tra và đảm bảo bóng không thể bay ngang.
        if (Math.abs(vy1) < minVy) {
            // Ép vy1 về giá trị tối thiểu (giữ nguyên dấu)
            vy1 = (vy1 >= 0) ? minVy : -minVy;

            // Tính lại vx1 để bảo toàn tổng tốc độ (speed^2 = vx1^2 + vy1^2)
            float newVxSq =  (ballSpeed * ballSpeed) - (vy1 * vy1);
            vx1 = (float) Math.sqrt(Math.max(0, newVxSq)) * Math.signum(vx1); // Math.max đề phòng lỗi làm tròn
        }

        if (Math.abs(vy2) < minVy) {
            // Ép vy2 về giá trị tối thiểu (giữ nguyên dấu)
            vy2 = (vy2 >= 0) ? minVy : -minVy;

            // Tính lại vx2 để bảo toàn tổng tốc độ
            float newVxSq = (ballSpeed * ballSpeed) - (vy2 * vy2);
            vx2 = (float) Math.sqrt(Math.max(0, newVxSq)) * Math.signum(vx2);
        }

        Ball ballTwin1 = new Ball((int) spawnPosition.getX(), (int) spawnPosition.getY());
        Ball ballTwin2 = new Ball((int) spawnPosition.getX(), (int) spawnPosition.getY());

        ballTwin2.setLinearVelocity(vx1, vy1);
        ballTwin1.setLinearVelocity(vx2, vy2);

        Game.getInstance().getCurrentLevel().addBall(ballTwin1);
        Game.getInstance().getCurrentLevel().addBall(ballTwin2);
    }

    @Override
    public void onUpdate(double deltaTime) {
        if (isHeldByPaddle && holdingPaddle != null && holdingPaddle.getEntity().isActive()) {

            double newX = holdingPaddle.getX() + holdOffsetX;
            this.setX((float) newX);

            this.setY((float) (holdingPaddle.getY() - this.getEntity().getHeight()));
        } else {
            // di chuyển vật lý bình thường
            super.onUpdate(deltaTime);
        }
    }
}
