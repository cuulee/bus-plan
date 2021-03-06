package com.azavea;

import java.util.Arrays;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.AnchorShadowVariable;
import org.optaplanner.core.api.domain.variable.InverseRelationShadowVariable;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import org.optaplanner.core.api.domain.variable.PlanningVariableGraphType;

import com.azavea.Bus;
import com.azavea.Node;
import com.azavea.SourceOrSinkOrAnchor;
import com.azavea.Student;


/**
 * The ancestor of all sources (e.g. stops) and sinks (e.g. schools).
 *
 * @author James McClain
 */
@PlanningEntity
public abstract class SourceOrSink implements SourceOrSinkOrAnchor {

    protected Bus bus;
    protected Node node;
    protected SourceOrSink next;
    protected SourceOrSinkOrAnchor previous;
    protected String schoolUuid;

    @Override public SourceOrSink getNext() { return this.next; }
    @Override public void setNext(SourceOrSink next) { this.next = next; }

    @PlanningVariable(valueRangeProviderRefs = {"busRange", "stopRange", "schoolRange"},
                      graphType = PlanningVariableGraphType.CHAINED)
    public SourceOrSinkOrAnchor getPrevious() { return this.previous; }
    public void setPrevious(SourceOrSinkOrAnchor previous) { this.previous = previous; }

    @Override public Node getNode() { return this.node; };
    @Override public void setNode(Node node) { this.node = node; }

    @AnchorShadowVariable(sourceVariableName = "previous")
    @Override public Bus getBus() { return this.bus; }
    @Override public void setBus(Bus bus) { this.bus = bus; }

    public String getSchoolUuid() { return this.schoolUuid; }
    public void setSchoolUuid(String schoolUuid) { this.schoolUuid = schoolUuid; }

    @Override
    public int hashCode() {
        return this.getNode().toString().hashCode();
    }

    public String toString() { return "SOURCE.or.SINK[" + node.toString() + "]"; }

    /**
     * The travel time between this and the other {@link SourceOrSinkOrAnchor}.
     *
     * @author James McClain
     * @param  other The other location
     * @return The travel time in seconds
     */
    public int time(SourceOrSinkOrAnchor other) {
        return this.getNode().time(other.getNode());
    }

    /**
     * The travel time between this and the other {@link Node}.
     *
     * @author James McClain
     * @param  other The other location
     * @return The travel time in seconds
     */
    public int time(Node other) {
        return this.getNode().time(other);
    }

    /**
     * The distance between this and the other {@link SourceOrSinkOrAnchor}.
     *
     * @author James McClain
     * @param  other The other location
     * @return The distance in feet
     */
    public double distance(SourceOrSinkOrAnchor other) {
        return this.getNode().distance(other.getNode());
    }

    /**
     * The distance between this and the other {@link Node}.
     *
     * @author James McClain
     * @param  other The other location
     * @return The distance in feet
     */
    public double distance(Node other) {
        return this.getNode().distance(other);
    }
}
