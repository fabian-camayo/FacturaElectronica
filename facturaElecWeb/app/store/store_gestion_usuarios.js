/*
 * File: app/store/store_gestion_usuarios.js
 *
 * This file was generated by Sencha Architect version 4.2.5.
 * http://www.sencha.com/products/architect/
 *
 * This file requires use of the Ext JS 6.6.x Modern library, under independent license.
 * License of Sencha Architect does not include license for Ext JS 6.6.x Modern. For more
 * details see http://www.sencha.com/license or contact license@sencha.com.
 *
 * This file will be auto-generated each and everytime you save your project.
 *
 * Do NOT hand edit this file.
 */

Ext.define('facturaElecWeb.store.store_gestion_usuarios', {
    extend: 'Ext.data.Store',

    requires: [
        'facturaElecWeb.model.model_gestion_usuarios',
        'Ext.data.proxy.Ajax',
        'Ext.data.reader.Json'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            storeId: 'store_gestion_usuarios',
            model: 'facturaElecWeb.model.model_gestion_usuarios',
            proxy: {
                type: 'ajax',
                api: {
                    read: 'sesionUsuario/listarUsuarios'
                },
                timeout: 60000,
                reader: {
                    type: 'json',
                    rootProperty: 'data'
                },
                listeners: {
                    onJsonOnAjaxOnAjaxException: {
                        fn: me.onAjaxOnJsonOnAjaxOnAjaxException
                    }
                }
            }
        }, cfg)]);
    },

    onAjaxOnJsonOnAjaxOnAjaxException: function(ajax) {
        facturaElec.app.showError(proxy, operation, this);
    }

});