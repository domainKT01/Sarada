package com.solproe.service.APIs.whatsapp;

public class GenerateMessage {


    public static String[] generate(String type, String level) {
        String[] message = new String[0];
        switch (type) {
            case "fireForest":
                if (level.equalsIgnoreCase("orange")) {
                    message = new String[] {
                            "*Alerta Incendio Forestal*\n",
                            "Alerta Naranja\n",
                            "ALISTAMIENTO DE BRIGADAS DE EMERGENCIAS"
                    };
                }
                else {
                    message = new String[] {
                            "*Alerta Incendio Forestal*\n",
                            "Alerta Roja\n",
                            "ACTIVAR EN PREVENTIVO EL PMU Y ALISTAMIENTO DE BRIGADAS DE EMERGENCIAS (EQUIPOS LISTOS PARA REACCIÓN INMEDIATA)"
                    };
                }
                break;
            case "massMovement":
                if (level.equalsIgnoreCase("orange")) {
                    message = new String[] {
                            "*Alerta Deslizamiento*\n",
                            "Alerta Naranja\n",
                            "ALISTAMIENTO DE BRIGADAS DE EMERGENCIAS"
                    };
                }
                else {
                    message = new String[] {
                            "*Alerta Deslizamiento*\n",
                            "Alerta Roja\n",
                            "ACTIVAR EN PREVENTIVO EL PMU Y ALISTAMIENTO DE BRIGADAS DE EMERGENCIAS (EQUIPOS LISTOS PARA REACCIÓN INMEDIATA)"
                    };
                }
                break;
            case "wind":
                if (level.equalsIgnoreCase("orange")) {
                    message = new String[] {
                            "*Alerta Vendaval*\n",
                            "Alerta Naranja\n",
                            "ALISTAMIENTO DE BRIGADAS DE EMERGENCIAS"
                    };
                }
                else {
                    message = new String[] {
                            "*Alerta Vendaval*\n",
                            "Alerta Roja\n",
                            "ACTIVAR EN PREVENTIVO EL PMU Y ALISTAMIENTO DE BRIGADAS DE EMERGENCIAS (EQUIPOS LISTOS PARA REACCIÓN INMEDIATA)"
                    };
                }
                break;
        }

        return message;
    }
}
