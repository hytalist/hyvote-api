package com.hytalist.hyvote.event

/**
 * Interface for events that can be cancelled.
 */
interface ICancellable {
    /**
     * Check if this event is cancelled.
     * @return true if cancelled
     */
    fun isCancelled(): Boolean

    /**
     * Set the cancelled state of this event.
     * @param cancelled true to cancel
     */
    fun setCancelled(cancelled: Boolean)
}
