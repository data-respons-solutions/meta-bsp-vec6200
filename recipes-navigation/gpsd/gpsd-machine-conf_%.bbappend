FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI = "file://gpsd.ublox"

inherit update-alternatives

ALTERNATIVE_${PN} = "gpsd-defaults"
ALTERNATIVE_LINK_NAME[gpsd-defaults] = "${sysconfdir}/default/gpsd"
ALTERNATIVE_TARGET[gpsd-defaults] = "${sysconfdir}/default/gpsd.ublox"
ALTERNATIVE_PRIORITY[gpsd-defaults] = "15"

do_install() {
    install -d ${D}/${sysconfdir}/default
    install -m 0644 ${WORKDIR}/gpsd.ublox ${D}/${sysconfdir}/default/
}
