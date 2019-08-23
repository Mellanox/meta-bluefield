FILESEXTRAPATHS_prepend := "${THISDIR}/systemd:"

# Turn on networkd by default and supply a basic network
# init script that will enable DHCP for any and all ethernet
# interfaces.

SRC_URI_append = " file://05-tmfifo_net0.network"
SRC_URI_append = " file://10-mlx-dhcp.network"
SRC_URI_append = " file://15-oob_net0.network"

do_install_append() {
    install -d ${D}${sysconfdir}/systemd/network/
    install -m 0644 ${WORKDIR}/05-tmfifo_net0.network ${D}${sysconfdir}/systemd/network/
    install -m 0644 ${WORKDIR}/10-mlx-dhcp.network ${D}${sysconfdir}/systemd/network/
    install -m 0644 ${WORKDIR}/15-oob_net0.network ${D}${sysconfdir}/systemd/network/
    sed -i -e '/\[Service\]/a ExecStartPre=/bin/sleep 8' ${D}${systemd_unitdir}/system/systemd-networkd.service
}
