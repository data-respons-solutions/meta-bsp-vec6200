FILESEXTRAPATHS_append := "${THISDIR}/files"
SRC_URI += "file://systemd-modules-load.service.in"

# module user-gpios fails to load with "EPROBE-DEFER" as the module is loaded before its dependencies.
# Add retry mechanism to systemd-modules-load service
# 3 retries, 1 second delay
do_configure_prepend() {
	install -m 0644 ${WORKDIR}/systemd-modules-load.service.in ${S}/units/systemd-modules-load.service.in
}
