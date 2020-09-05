package com.ome_r.plots;

import com.ome_r.PrisonCore;
import org.bukkit.Location;

import java.util.UUID;

public class Plot {

    private int x, z;
    private Location loc;
    private UUID owner;

    public Plot(UUID owner, int x, int z){
        this.x = x;
        this.z = z;
    }

    public int getX(){
        return x;
    }

    public int getZ(){
        return z;
    }

    /**public Plot nextPlot(){
        int x = this.x, z = this.z;
        if(x >= z && x < -z + 1)
            x++;
        else if(x >= -z + 1 && x <= z)
            x--;
        else if(z >= -x + 1 && z < x)
            z++;
        else if(z > x && z <= -x + 1)
            z--;

        return PrisonCore.getPlots().get(x, z);
    }**/

    private Location genLocation(){
        double pr = PrisonCore.getPlots().plotLength + PrisonCore.getPlots().roadLength,
                x = this.x * pr - (pr / 2), z = this.z * pr;

        return new Location(PrisonCore.getPlots().world, x, 100, z);
    }

}
