package com.example.mdt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.BatteryManager;
import android.os.Build;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import static androidx.core.content.ContextCompat.getSystemService;

public class BatteryReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        TextView statusLabel = ((Batteryinfo)context).findViewById(R.id.statusLabel);
        TextView percentageLabel = ((Batteryinfo)context).findViewById(R.id.percentageLabel);
        ImageView batteryImage = ((Batteryinfo)context).findViewById(R.id.batteryImage);
        TextView healthLabel = ((Batteryinfo)context).findViewById(R.id.healthLabel);
        TextView sourceLabel = ((Batteryinfo)context).findViewById(R.id.sourceLabel);
        TextView techLabel = ((Batteryinfo)context).findViewById(R.id.techLabel);
        TextView tempLabel = ((Batteryinfo)context).findViewById(R.id.tempLabel);
        TextView voltageLabel = ((Batteryinfo)context).findViewById(R.id.voltageLabel);
        TextView capacityLabel = ((Batteryinfo)context).findViewById(R.id.capacityLabel);

        String action = intent.getAction();

        if(action != null && action.equals(Intent.ACTION_BATTERY_CHANGED)){

            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            String message = "";

            switch (status){
                case BatteryManager.BATTERY_STATUS_FULL:
                    message = "FULL";
                    break;
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    message = "Charging";
                    break;
                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    message = "Discharging";
                    break;
                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                    message = "Not Charging";
                    break;
                case BatteryManager.BATTERY_STATUS_UNKNOWN:
                    message = "Unknown";
                    break;
            }

            statusLabel.setText(message);

            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int percentage = level * 100 / scale;
            percentageLabel.setText(percentage + "%");

            Resources res = context.getResources();
            if (percentage >=90){
                batteryImage.setImageDrawable(res.getDrawable(R.drawable.fullbatterylevels));
            }else if (90> percentage && percentage>=65){
                batteryImage.setImageDrawable(res.getDrawable(R.drawable.lessfullbatterylevels));
            }else if (65> percentage && percentage>=40){
                batteryImage.setImageDrawable(res.getDrawable(R.drawable.midbatterylevels));
            }else if (40> percentage && percentage>=10){
                batteryImage.setImageDrawable(res.getDrawable(R.drawable.lessbatterylevels));
            }else {
                batteryImage.setImageDrawable(res.getDrawable(R.drawable.emptybatterylevels));
            }

            //int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);

            boolean present = intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);

            if (present) {
                int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
                String healthLbl = "";

                switch (health) {
                    case BatteryManager.BATTERY_HEALTH_COLD:
                        healthLbl = "COLD";
                        break;

                    case BatteryManager.BATTERY_HEALTH_DEAD:
                        healthLbl = "DEAD";
                        break;

                    case BatteryManager.BATTERY_HEALTH_GOOD:
                        healthLbl = "GOOD";
                        break;

                    case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                        healthLbl = "OVERVOLTAGE";
                        break;

                    case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                        healthLbl = "OVERHEAT";
                        break;

                    case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                        healthLbl = "FAILED";
                        break;

                    case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                        healthLbl = "UNKNOWN";
                        break;
                }

                int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
                String pluggedLbl = "";
                switch (plugged) {
                    case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                        pluggedLbl = "Wireless";
                        break;

                    case BatteryManager.BATTERY_PLUGGED_USB:
                        pluggedLbl = "USB";
                        break;

                    case BatteryManager.BATTERY_PLUGGED_AC:
                        pluggedLbl = "AC Charger";
                        break;

                    default:
                        pluggedLbl = "None";
                        break;
                }

            //int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
            String technology = intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
            int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
            int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
            long capacity = getBatteryCapacity(context);
            

            healthLabel.setText(healthLbl);
            sourceLabel.setText(pluggedLbl);
            techLabel.setText(technology);
            tempLabel.setText(temperature+"");
            voltageLabel.setText(voltage+"mV");
            capacityLabel.setText(capacity+"");

        }
    }
    }

    public long getBatteryCapacity(Context ctx) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            BatteryManager mBatteryManager = (BatteryManager) ctx.getSystemService(Context.BATTERY_SERVICE);
            Long chargeCounter = mBatteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER);
            Long capacity = mBatteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

            if (chargeCounter != null && capacity != null) {
                long value = (long) (((float) chargeCounter / (float) capacity) * 100f);
                return value;
            }
        }

        return 0;
    }
}
